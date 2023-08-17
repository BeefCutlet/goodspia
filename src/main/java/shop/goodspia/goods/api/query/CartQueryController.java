package shop.goodspia.goods.api.query;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.dto.CartResponseDto;
import shop.goodspia.goods.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartQueryController {

    private final CartService cartService;

//    @GetMapping("/list")
//    public ResponseEntity<List<CartResponseDto>> getCartList() {
//        return ResponseEntity.ok();
//    }
}
