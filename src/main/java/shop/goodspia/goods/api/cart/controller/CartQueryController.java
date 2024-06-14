package shop.goodspia.goods.api.cart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.api.cart.dto.CartResponse;
import shop.goodspia.goods.api.cart.service.CartService;
import shop.goodspia.goods.global.security.dto.MemberPrincipal;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartQueryController {

    private final CartService cartService;

    @GetMapping("/list")
    public ResponseEntity<List<CartResponse>> getMemberCartList(@AuthenticationPrincipal MemberPrincipal principal) {
        Long memberId = principal.getId();
        List<CartResponse> cartList = cartService.getCartList(memberId);
        return ResponseEntity.ok(cartList);
    }

//    @GetMapping("/list")
//    public ResponseEntity<List<RedisCart>> getMemberRedisCartList(HttpServletRequest request) {
//        Long memberId = (Long) request.getAttribute("memberId");
//        List<RedisCart> cartList = cartService.getRedisCartList(memberId);
//        return ResponseEntity.ok(cartList);
//    }
}
