package shop.goodspia.goods.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.dto.goods.GoodsDetailResponseDto;
import shop.goodspia.goods.dto.goods.GoodsRequestDto;
import shop.goodspia.goods.dto.goods.GoodsResponseDto;
import shop.goodspia.goods.entity.Design;
import shop.goodspia.goods.entity.Goods;
import shop.goodspia.goods.exception.ArtistNotFoundException;
import shop.goodspia.goods.exception.GoodsNotFoundException;
import shop.goodspia.goods.repository.ArtistRepository;
import shop.goodspia.goods.repository.DesignRepository;
import shop.goodspia.goods.repository.GoodsQueryRepository;
import shop.goodspia.goods.repository.GoodsRepository;

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
     * @param goodsRequestDto
     * @return
     */
    public Long addGoods(long artistId, GoodsRequestDto goodsRequestDto) {
        //회원 조회 - 아티스트 등록 여부 파악용
        return artistRepository.findById(artistId).map(
                artist -> {
                    //굿즈 엔티티 생성
                    Goods goods = Goods.createGoods(goodsRequestDto, artist);
                    //작성한 디자인 옵션의 엔티티 생성 후 DB에 저장
                    for (String design : goodsRequestDto.getDesigns()) {
                        designRepository.save(Design.createDesign(design, goods));
                    }
                    return goodsRepository.save(goods).getId();
                }
        ).orElseThrow(() -> new ArtistNotFoundException("아티스트 정보가 존재하지 않습니다."));
    }

    /**
     * 굿즈 정보 수정용 메서드
     * @param goodsRequestDto
     */
    public void modifyGoods(GoodsRequestDto goodsRequestDto) {
        goodsRepository.findById(goodsRequestDto.getId())
                .orElseThrow(() -> new GoodsNotFoundException("수정할 굿즈 정보가 없습니다."));
    }

    /**
     * 굿즈 삭제 메서드 - 상태 변경
     */
    public void delete(long goodsId) {
        goodsRepository.findById(goodsId)
                .orElseThrow(() -> new GoodsNotFoundException("삭제할 굿즈 정보가 없습니다."));
    }

    //전체 굿즈리스트 조회 (최신순)
    public Page<GoodsResponseDto> getGoodsList(Pageable pageable, String category) {
        return goodsQueryRepository.findGoodsList(pageable, category);
    }

    public Page<GoodsResponseDto> getArtistGoodsList(Pageable pageable, long artistId) {
        return goodsQueryRepository.findArtistGoodsList(pageable, artistId);
    }

    public GoodsDetailResponseDto getGoods(long goodsId) {
        Goods goodsDetail = goodsQueryRepository.findGoodsDetail(goodsId);
        return new GoodsDetailResponseDto(goodsDetail);
    }
}
