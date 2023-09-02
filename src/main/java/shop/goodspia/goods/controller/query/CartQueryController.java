package shop.goodspia.goods.controller.query;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.dto.cart.CartResponse;
import shop.goodspia.goods.security.dto.SessionUser;
import shop.goodspia.goods.service.CartService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartQueryController {

    private final CartService cartService;

    @GetMapping("/list")
    public ResponseEntity<List<CartResponse>> getMemberCartList(HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        List<CartResponse> cartList = cartService.getCartList(sessionUser.getMemberId());
        return ResponseEntity.ok(cartList);
    }
}
