package shop.goodspia.goods.controller.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.dto.Response;
import shop.goodspia.goods.dto.goods.GoodsDetailResponse;
import shop.goodspia.goods.dto.goods.GoodsResponse;
import shop.goodspia.goods.service.GoodsService;

@Slf4j
@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsQueryController {

    private final GoodsService goodsService;

    /**
     * 최신 굿즈 리스트 - 메인페이지
     * @param category
     * @param pageable
     * @return
     */
    @GetMapping("/list")
    public Response<Page<GoodsResponse>> getGoodsList(@RequestParam(required = false) String category, Pageable pageable) {
        log.info("전체 굿즈 리스트 조회, pageSize={}, pageNum={}", pageable.getPageSize(), pageable.getPageNumber());
        Page<GoodsResponse> goodsList = goodsService.getGoodsList(pageable, category);
        return Response.of(HttpStatus.OK.value(), "굿즈 리스트 조회 성공", goodsList);
    }

    /**
     * 굿즈 상세 정보 조회 - 굿즈 상세페이지
     * @param goodsId
     * @return
     */
    @GetMapping("/detail/{goodsId}")
    public Response<GoodsDetailResponse> getOneGoods(@PathVariable long goodsId) {
        log.info("굿즈 상세 정보 조회, goodsId={}", goodsId);
        GoodsDetailResponse goods = goodsService.getGoods(goodsId);
        return Response.of(HttpStatus.OK.value(), "굿즈 상세 정보 조회 성공", goods);
    }

    /**
     * 아티스트가 제작한 굿즈 리스트 - 아티스트 페이지
     * @param artistId
     * @param pageable
     * @return
     */
    @GetMapping("/artist/{artistId}")
    public Response<Page<GoodsResponse>> getArtistGoodsList(@PathVariable long artistId, Pageable pageable) {
        log.info("아티스트가 제작한 굿즈 리스트 조회, artistId={}", artistId);
        Page<GoodsResponse> artistGoodsList = goodsService.getArtistGoodsList(pageable, artistId);
        return Response.of(HttpStatus.OK.value(), "제작된 굿즈 리스트 조회 성공", artistGoodsList);
    }
}
