package shop.goodspia.goods.controller.query;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.dto.cart.CartResponse;
import shop.goodspia.goods.service.CartService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "장바구니 조회 API", description = "장바구니 목록 조회 API")
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartQueryController {

    private final CartService cartService;

    @Operation(summary = "장바구니 목록 조회 API", description = "접속 중인 회원이 등록한 장바구니 목록을 조회합니다.")
    @GetMapping("/list")
    public ResponseEntity<List<CartResponse>> getMemberCartList(@Parameter(hidden = true) HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");
        List<CartResponse> cartList = cartService.getCartList(memberId);
        return ResponseEntity.ok(cartList);
    }
}
