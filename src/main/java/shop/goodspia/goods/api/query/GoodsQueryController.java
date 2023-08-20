package shop.goodspia.goods.api.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.dto.GoodsDetailResponseDto;
import shop.goodspia.goods.dto.GoodsResponseDto;
import shop.goodspia.goods.service.GoodsService;

@Slf4j
@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsQueryController {

    private final GoodsService goodsService;

    @GetMapping("/list")
    public ResponseEntity<Page<GoodsResponseDto>> getGoodsList(Pageable pageable) {
        log.info("GoodsQueryController.getGoodsList Start, pageSize={}, pageNum={}", pageable.getPageSize(), pageable.getPageNumber());
        Page<GoodsResponseDto> goodsList = goodsService.getGoodsList(pageable);
        return ResponseEntity.ok(goodsList);
    }

    @GetMapping("/detail/{goodsId}")
    public ResponseEntity<GoodsDetailResponseDto> getOneGoods(@PathVariable long goodsId) {
        log.info("GoodsQueryController.getOneGoods Start, goodsId={}", goodsId);
        GoodsDetailResponseDto goods = goodsService.getGoods(goodsId);
        return ResponseEntity.ok(goods);
    }

    @GetMapping("/goods-list/{artistId}")
    public ResponseEntity<Page<GoodsResponseDto>> getArtistGoodsList(@PathVariable long artistId, Pageable pageable) {
        log.info("GoodsQueryController.getArtistGoodsList Start, artistId={}", artistId);
        Page<GoodsResponseDto> artistGoodsList = goodsService.getArtistGoodsList(pageable, artistId);
        return ResponseEntity.ok(artistGoodsList);
    }
}
