package shop.goodspia.goods.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.cart.dto.CartSaveRequest;
import shop.goodspia.goods.cart.service.CartService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @Value("${base-url}")
    private String baseUrl;

//    /**
//     * 카트에 선택한 굿즈를 등록하는 API
//     * @param cart goodsId, designId
//     * @param request
//     * @return
//     */
//    @PostMapping("/non-cache")
//    public ResponseEntity<?> addCart(@RequestBody @Valid CartSaveRequest cart,
//                                     HttpServletRequest request) {
//        Long memberId = (Long) request.getAttribute("memberId");
//        cartService.addCart(memberId, cart);
//        return ResponseEntity.created(URI.create(baseUrl + "/cart/list")).build();
//    }

    /**
     * 카트에 선택한 굿즈를 등록하는 API
     * @param cart goodsId, designId
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<?> addRedisCart(@RequestBody @Valid CartSaveRequest cart,
                                          HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");
        cartService.addRedisCart(memberId, cart);
        return ResponseEntity.created(URI.create(baseUrl + "/cart/list")).build();
    }

    /**
     * 장바구니에 담긴 굿즈의 수량 정보 수정 API
     * @param cartId
     * @param quantity
     * @return
     */
    @PutMapping("/{cartId}/quantity")
    public ResponseEntity<?> fluctuateQuantity(@PathVariable Long cartId,
                                               @RequestParam Integer quantity) {
        cartService.changeQuantity(cartId, quantity);
        return ResponseEntity.noContent().build();
    }

    /**
     * 장바구니에 등록된 상품 삭제
     * @param cartId
     * @return
     */
    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }
}
