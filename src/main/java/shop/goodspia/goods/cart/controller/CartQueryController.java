package shop.goodspia.goods.cart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.cart.entity.RedisCart;
import shop.goodspia.goods.cart.service.CartService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartQueryController {

    private final CartService cartService;

//    @GetMapping("/list/non-cache")
//    public ResponseEntity<Page<CartResponse>> getMemberCartList(Pageable pageable, HttpServletRequest request) {
//        Long memberId = (Long) request.getAttribute("memberId");
//        Page<CartResponse> cartList = cartService.getCartList(memberId, pageable);
//        return ResponseEntity.ok(cartList);
//    }

    @GetMapping("/list")
    public ResponseEntity<List<RedisCart>> getMemberRedisCartList(HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");
        List<RedisCart> cartList = cartService.getRedisCartList(memberId);
        return ResponseEntity.ok(cartList);
    }
}
