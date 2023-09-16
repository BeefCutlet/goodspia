package shop.goodspia.goods.cart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.cart.dto.CartSaveRequest;
import shop.goodspia.goods.cart.service.CartService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "장바구니 등록/수정/삭제 API", description = "장바구니에 새로운 굿즈를 등록 및 삭제, 수량을 수정하는 API")
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * 카트에 선택한 굿즈를 등록하는 API
     * @param cart goodsId, designId
     * @param request
     * @return
     */
    @Operation(summary = "장바구니 등록 API", description = "현재 접속 중인 회원이 등록한 장바구니 목록을 조회하는 API")
    @PostMapping
    public String addCart(@Parameter(name = "장바구니 정보", required = true) @RequestBody @Valid CartSaveRequest cart,
                          @Parameter(hidden = true) HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");
        cartService.addCart(memberId, cart);
        return "";
    }

    /**
     * 장바구니에 담긴 굿즈의 수량 정보 수정 API
     * @param cartId
     * @param quantity
     * @return
     */
    @Operation(summary = "장바구니 수량 수정 API", description = "장바구니에 담긴 굿즈의 수량을 수정하는 API")
    @PutMapping("/fluctuate/{cartId}")
    public String fluctuateQuantity(@Parameter(description = "수정할 장바구니 굿즈의 번호") @PathVariable Long cartId,
                                    @Parameter(description = "수정될 굿즈 수량") @RequestParam Integer quantity) {
        cartService.changeQuantity(cartId, quantity);
        return "";
    }

    /**
     * 장바구니에 등록된 상품 삭제
     * @param cartId
     * @return
     */
    @Operation(summary = "장바구니 굿즈 삭제 API", description = "장바구니에 담긴 굿즈를 삭제하는 API")
    @DeleteMapping("/delete/{cartId}")
    public String deleteCart(@Parameter(description = "삭제처리할 장바구니 번호", example = "123") @PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return "";
    }
}
