package shop.goodspia.goods.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.cart.dto.CartSaveListRequest;
import shop.goodspia.goods.cart.dto.CartUpdateQuantityRequest;
import shop.goodspia.goods.cart.service.CartService;
import shop.goodspia.goods.security.dto.MemberPrincipal;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @Value("${base-url}")
    private String baseUrl;

    /**
     * 카트에 선택한 굿즈를 등록하는 API
     */
    @PostMapping
    public ResponseEntity<?> addCart(@RequestBody @Valid CartSaveListRequest cartList,
                                     @AuthenticationPrincipal MemberPrincipal principal) {
        Long memberId = principal.getId();
        cartService.addCarts(memberId, cartList);
        return ResponseEntity.noContent().build();
    }

//    /**
//     * 카트에 선택한 굿즈를 등록하는 API
//     */
//    @PostMapping
//    public ResponseEntity<?> addRedisCart(@RequestBody @Valid CartSaveRequest cart,
//                                          @AuthenticationPrincipal MemberPrincipal principal) {
//        Long memberId = principal.getId();
//        cartService.addRedisCart(memberId, cart);
//        return ResponseEntity.created(URI.create(baseUrl + "/cart/list")).build();
//    }

    /**
     * 장바구니에 담긴 굿즈의 수량 정보 수정 API
     */
    @PutMapping("/quantity")
    public ResponseEntity<?> changeQuantity(@RequestBody @Valid CartUpdateQuantityRequest quantityRequest) {
        cartService.changeQuantity(quantityRequest.getCartId(), quantityRequest.getQuantity());
        return ResponseEntity.noContent().build();
    }

    /**
     * 장바구니에 등록된 상품 삭제
     */
    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }
}
