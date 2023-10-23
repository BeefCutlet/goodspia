package shop.goodspia.goods.cart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.cart.dto.CartResponse;
import shop.goodspia.goods.cart.entity.RedisCart;
import shop.goodspia.goods.cart.service.CartService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "장바구니 조회 API", description = "장바구니 목록 조회 API")
@Slf4j
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartQueryController {

    private final CartService cartService;

    @Operation(summary = "현재 접속 중인 회원이 등록한 장바구니 목록 조회 API", description = "page와 size를 파라미터로 설정할 수 있습니다.")
    @GetMapping("/list/non-cache")
    public ResponseEntity<Page<CartResponse>> getMemberCartList(@Parameter(hidden = true) Pageable pageable,
                                                                @Parameter(hidden = true) HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");
        Page<CartResponse> cartList = cartService.getCartList(memberId, pageable);
        return ResponseEntity.ok(cartList);
    }

    @Operation(summary = "장바구니 목록 조회 API", description = "현재 접속 중인 회원이 등록한 장바구니의 전체 목록을 조회합니다.")
    @GetMapping("/list")
    public ResponseEntity<List<RedisCart>> getMemberRedisCartList(@Parameter(hidden = true) HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");
        List<RedisCart> cartList = cartService.getRedisCartList(memberId);
        return ResponseEntity.ok(cartList);
    }
}
