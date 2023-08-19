package shop.goodspia.goods.api.query;

import lombok.RequiredArgsConstructor;
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

import java.util.List;

@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsQueryController {

    private final GoodsService goodsService;

    @GetMapping("/list")
    public ResponseEntity<Page<GoodsResponseDto>> getGoodsList(Pageable pageable) {
        Page<GoodsResponseDto> goodsList = goodsService.getGoodsList(pageable);
        return ResponseEntity.ok(goodsList);
    }

    @GetMapping("/goods/{goodsId}")
    public ResponseEntity<GoodsDetailResponseDto> getOneGoods(@PathVariable long goodsId) {
        GoodsDetailResponseDto goods = goodsService.getGoods(goodsId);
        return ResponseEntity.ok(goods);
    }
}
