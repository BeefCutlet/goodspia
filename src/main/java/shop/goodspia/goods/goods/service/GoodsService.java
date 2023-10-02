package shop.goodspia.goods.goods.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.goods.dto.*;
import shop.goodspia.goods.goods.entity.Design;
import shop.goodspia.goods.goods.entity.Goods;
import shop.goodspia.goods.artist.repository.ArtistRepository;
import shop.goodspia.goods.goods.repository.DesignRepository;
import shop.goodspia.goods.goods.repository.GoodsQueryRepository;
import shop.goodspia.goods.goods.repository.GoodsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GoodsService {

    private final GoodsRepository goodsRepository;
    private final DesignRepository designRepository;
    private final ArtistRepository artistRepository;

    private final GoodsQueryRepository goodsQueryRepository;

    /**
     * 굿즈 등록용 메서드
     * @param artistId
     * @param goodsSaveRequest
     * @return
     */
    public Long addGoods(Long artistId, GoodsSaveRequest goodsSaveRequest) {
        //회원 조회 - 아티스트 등록 여부 파악용
        return artistRepository.findById(artistId).map(
                artist -> {
                    //굿즈 엔티티 생성
                    Goods goods = Goods.createGoods(goodsSaveRequest, artist);
                    //작성한 디자인 옵션의 엔티티 생성 후 DB에 저장
                    for (String design : goodsSaveRequest.getDesigns()) {
                        designRepository.save(Design.createDesign(design, goods));
                    }
                    return goodsRepository.save(goods).getId();
                }
        ).orElseThrow(() -> new IllegalArgumentException("아티스트 정보를 찾을 수 없습니다."));
    }

    /**
     * 굿즈 정보 수정용 메서드
     * @param goodsUpdateRequest
     */
    public void modifyGoods(Long goodsId, GoodsUpdateRequest goodsUpdateRequest) {
        //굿즈 엔티티 조회
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new IllegalArgumentException("수정할 굿즈 정보를 찾을 수 없습니다."));

        //굿즈 정보 수정
        goods.updateGoods(goodsUpdateRequest);
    }

    /**
     * 굿즈 삭제 메서드 - 상태 변경
     */
    public void delete(Long goodsId) {
        //굿즈 엔티티 조회
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new IllegalArgumentException("삭제할 굿즈 정보를 찾을 수 없습니다."));

        //굿즈 삭제 - 삭제 여부/삭제시각 갱신
        goods.delete();
    }

    /**
     * 전체 굿즈리스트 조회 (최신순)
     * @param pageable
     * @param category
     * @return
     */
    public GoodsListResponse getGoodsList(Pageable pageable, String category) {
        Page<GoodsResponse> goodsListPage = goodsQueryRepository.findGoodsList(pageable, category);
        List<GoodsResponse> goodsList = goodsListPage.getContent();
        return new GoodsListResponse(goodsList, goodsListPage.getTotalPages());
    }

    /**
     * 아티스트가 제작한 굿즈 리스트 조회 (최신순)
     * @param pageable
     * @param artistId
     * @return
     */
    public Page<GoodsResponse> getArtistGoodsList(Pageable pageable, long artistId) {
        return goodsQueryRepository.findArtistGoodsList(pageable, artistId);
    }

    /**
     * 굿즈 상세 정보 조회
     * @param goodsId
     * @return
     */
    public GoodsDetailResponse getGoods(long goodsId) {
        Goods goodsDetail = goodsQueryRepository.findGoodsDetail(goodsId);
        return new GoodsDetailResponse(goodsDetail);
    }
}
