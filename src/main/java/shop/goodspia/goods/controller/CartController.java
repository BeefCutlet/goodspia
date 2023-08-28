package shop.goodspia.goods.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.dto.cart.CartRequestDto;
import shop.goodspia.goods.security.dto.SessionUser;
import shop.goodspia.goods.service.CartService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * 카트에 선택한 굿즈를 등록하는 API
     * @param cartRequestDto goodsId, designId
     * @param session
     * @return
     */
    @PostMapping("/add")
    public String addCart(@RequestBody CartRequestDto cartRequestDto,
                          HttpSession session) {
        long memberId = ((SessionUser) session.getAttribute("sessionUser")).getMemberId();
        cartRequestDto.setMemberId(memberId);
        cartService.addCart(cartRequestDto);
        return "";
    }


    @PatchMapping("/fluctuate/{cartId}")
    public String fluctuateQuantity(@PathVariable long cartId,
                                    @RequestParam int quantity) {
        cartService.changeQuantity(cartId, quantity);
        return "";
    }

    /**
     * 장바구니에 등록된 상품 삭제
     * @param cartId
     * @return
     */
    @DeleteMapping("/delete/{cartId}")
    public String deleteCart(@PathVariable long cartId) {
        cartService.deleteCart(cartId);
        return "";
    }
}
