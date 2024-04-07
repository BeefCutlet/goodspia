package shop.goodspia.goods.goods.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.goods.dto.GoodsDetailResponse;
import shop.goodspia.goods.goods.dto.GoodsListResponse;
import shop.goodspia.goods.goods.service.GoodsService;

@Slf4j
@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsQueryController {

    private final GoodsService goodsService;

    /**
     * 최신 굿즈 리스트 - 메인페이지
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<GoodsListResponse> getGoodsList(Pageable pageable) {
        log.info("전체 굿즈 리스트 조회, size={}, page={}", pageable.getPageSize(), pageable.getPageNumber());
        GoodsListResponse goodsList = goodsService.getGoodsList(pageable);
        return ResponseEntity.ok(goodsList);
    }

    /**
     * 굿즈 상세 정보 조회 - 굿즈 상세페이지
     */
    @GetMapping("/{goodsId}")
    public ResponseEntity<GoodsDetailResponse> getGoods(@PathVariable Long goodsId) {
        log.info("굿즈 상세 정보 조회, goodsId={}", goodsId);
        GoodsDetailResponse goods = goodsService.getGoods(goodsId);
        return ResponseEntity.ok(goods);
    }

    /**
     * 아티스트가 제작한 굿즈 리스트 - 아티스트 페이지
     */
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<GoodsListResponse> getArtistGoodsList(@PathVariable Long artistId, Pageable pageable) {
        log.info("아티스트가 제작한 굿즈 리스트 조회, artistId={}", artistId);
        GoodsListResponse artistGoodsList = goodsService.getArtistGoodsList(pageable, artistId);
        return ResponseEntity.ok(artistGoodsList);
    }
}
