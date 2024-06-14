package shop.goodspia.goods.api.goods.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.goodspia.goods.api.goods.dto.*;
import shop.goodspia.goods.api.goods.dto.design.DesignRequest;
import shop.goodspia.goods.api.goods.entity.Design;
import shop.goodspia.goods.api.goods.repository.DesignRepository;
import shop.goodspia.goods.api.goods.repository.GoodsQueryRepository;
import shop.goodspia.goods.api.goods.repository.GoodsRepository;
import shop.goodspia.goods.api.artist.entity.Artist;
import shop.goodspia.goods.api.artist.repository.ArtistRepository;
import shop.goodspia.goods.global.common.util.ImagePath;
import shop.goodspia.goods.global.common.util.ImageUpload;
import shop.goodspia.goods.api.goods.dto.*;
import shop.goodspia.goods.api.goods.entity.Goods;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GoodsService {

    private final GoodsRepository goodsRepository;
    private final DesignRepository designRepository;
    private final ArtistRepository artistRepository;
    private final ImageUpload imageUpload;

    private final GoodsQueryRepository goodsQueryRepository;

    /**
     * 굿즈 등록용 메서드
     */
    public void addGoods(Long memberId,
                         GoodsSaveRequest goodsSaveRequest,
                         MultipartFile thumbnail,
                         MultipartFile contentImage) {
        //DB에서 아티스트 정보 조회
        Artist artist = getArtistByMemberId(memberId);

        //굿즈 내용 이미지 저장
        String contentImageUrl = imageUpload.uploadImage(contentImage, ImagePath.GOODS_CONTENT);
        //굿즈 썸네일 이미지 저장
        String thumbnailUrl = imageUpload.uploadImage(thumbnail, ImagePath.GOODS_THUMBNAIL);

        //굿즈 정보 DB에 저장
        Goods goods = Goods.from(goodsSaveRequest, artist, contentImageUrl, thumbnailUrl);
        goodsRepository.save(goods);

        //굿즈 디자인 정보 DB에 저장
        for (String design : goodsSaveRequest.getDesigns()) {
            designRepository.save(Design.from(design, goods));
        }
    }

    /**
     * 굿즈 정보 수정용 메서드
     */
    public void modifyGoods(Long memberId,
                            GoodsUpdateRequest goodsUpdateRequest,
                            MultipartFile thumbnail,
                            MultipartFile content) {
        //굿즈 정보 조회
        Goods goods = getNotDeletedGoods(goodsUpdateRequest.getGoodsId());

        //아티스트 정보 조회
        Artist artist = getArtistByMemberId(memberId);

        if (!goods.getArtist().getId().equals(artist.getId())) {
            throw new IllegalStateException("현재 아티스트가 등록한 굿즈가 아닙니다.");
        }

        String thumbnailImagePath = null;
        if (thumbnail != null) {
            thumbnailImagePath = imageUpload.uploadImage(thumbnail, ImagePath.GOODS_THUMBNAIL);
        }

        String contentImagePath = null;
        if (content != null) {
            contentImagePath = imageUpload.uploadImage(content, ImagePath.GOODS_CONTENT);
        }

        //굿즈 정보 수정
        goods.updateGoods(goodsUpdateRequest, thumbnailImagePath, contentImagePath);

        //기존 굿즈 정보 조회
        List<Design> savedDesigns = designRepository.findAllByGoodsId(goodsUpdateRequest.getGoodsId());

        //디자인 정보 수정
        //=> 새로운 디자인의 ID가 null이면 새로운 디자인 정보 저장
        //=> 기존 디자인 정보와 일치하는 디자인 ID가 있으면 수정
        //=> 기존 디자인 ID 중에 요청 디자인 ID와 일치하지 않는 ID가 있으면 해당 디자인 정보는 삭제
        boolean[] isDesignExist = new boolean[savedDesigns.size()]; //기존 디자인 중 요청 디자인에 존재하는 디자인 확인용
        for (DesignRequest designRequest : goodsUpdateRequest.getDesigns()) {
            //요청 디자인 중 기존에 없던 디자인은 DB에 저장
            if (designRequest.getDesignId() == null) {
                designRepository.save(Design.from(designRequest.getDesignName(), goods));
                continue;
            }

            //요청 디자인 중 기존에 존재하는 디자인은 업데이트
            int index = 0;
            for (Design savedDesign : savedDesigns) {
                if (savedDesign.getId().equals(designRequest.getDesignId())) {
                    savedDesign.modifyDesignName(designRequest.getDesignName());
                    isDesignExist[index] = true;
                    break;
                }
                index++;
            }
        }

        //기존 디자인 중 안 쓰는 디자인 삭제
        for (int savedDesignIndex = 0; savedDesignIndex < savedDesigns.size(); savedDesignIndex++) {
            if (!isDesignExist[savedDesignIndex]) {
                designRepository.deleteById(savedDesigns.get(savedDesignIndex).getId());
            }
        }
    }

    /**
     * 굿즈 삭제 메서드 - 상태 변경
     */
    public void delete(Long goodsId) {
        //굿즈 엔티티 조회
        Goods goods = getNotDeletedGoods(goodsId);

        //굿즈 삭제 - 삭제 여부/삭제시각 갱신
        goods.delete();
    }

    /**
     * 전체 굿즈리스트 조회 (최신순)
     */
//    @Cacheable(value = "goodsList", key = "#pageable.pageNumber")
    public GoodsListResponse getGoodsList(Pageable pageable) {
        Page<GoodsResponse> goodsListPage = goodsQueryRepository.findGoodsList(pageable);
        List<GoodsResponse> goodsList = goodsListPage.getContent();
        return new GoodsListResponse(goodsList, goodsListPage.getTotalPages());
    }

    /**
     * 아티스트가 제작한 굿즈 리스트 조회 (최신순)
     */
    public GoodsArtistListResponse getArtistGoodsList(Long memberId) {
        //아티스트 정보 조회
        Artist artist = artistRepository.findArtistByMemberId(memberId).orElseThrow((() -> {
            throw new IllegalArgumentException("아티스트 정보를 찾을 수 없습니다.");
        }));

        //아티스트가 등록한 굿즈 목록을 DB에서 조회 (전체)
        List<GoodsArtistResponse> artistGoodsList = goodsQueryRepository.findArtistGoodsList(artist.getId());
        return new GoodsArtistListResponse(artistGoodsList);
    }

    /**
     * 굿즈 상세 정보 조회
     */
    public GoodsDetailResponse getGoods(Long goodsId) {
        Goods goodsDetail = goodsQueryRepository.findGoodsDetail(goodsId);
        return GoodsDetailResponse.from(goodsDetail);
    }

    /**
     * 아티스트가 등록한 굿즈 상세 정보 조회
     */
    public GoodsArtistResponse getRegisteredGoods(Long memberId, Long goodsId) {
        //아티스트 정보 조회
        Artist artist = getArtistByMemberId(memberId);

        //굿즈 정보 조회
        Goods goods = getNotDeletedGoods(goodsId);

        //현재 아티스트가 등록한 굿즈가 아니면 예외 발생
        if (!goods.getArtist().getId().equals(artist.getId())) {
            throw new IllegalStateException("현재 아티스트가 등록한 굿즈가 아닙니다.");
        }

        //굿즈 디자인 정보 조회
        List<Design> designs = designRepository.findAllByGoodsId(goodsId);

        return GoodsArtistResponse.from(goods, designs);
    }

    //삭제되지 않은 굿즈 정보 조회
    private Goods getNotDeletedGoods(Long goodsId) {
        return goodsRepository.findByGoodsIdNotDeleted(goodsId).orElseThrow(() -> {
            throw new IllegalArgumentException("수정할 굿즈 정보를 찾을 수 없습니다.");
        });
    }

    //현재 회원의 아티스트 정보 조회
    private Artist getArtistByMemberId(Long memberId) {
        return artistRepository.findArtistByMemberId(memberId).orElseThrow(() -> {
            throw new IllegalArgumentException("등록되지 않은 아티스트 입니다.");
        });
    }
}
