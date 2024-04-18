package shop.goodspia.goods.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.order.dto.OrderSaveListRequest;
import shop.goodspia.goods.order.service.OrderService;
import shop.goodspia.goods.security.dto.MemberPrincipal;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Value("${base-url}")
    private String baseUrl;

    /**
     * DB에 주문 정보 저장
     * 굿즈 상세페이지의 주문하기 버튼, 장바구니의 주문하기 버튼 클릭 시 동작
     */
    @PostMapping
    public ResponseEntity<?> addOrders(@RequestBody @Valid OrderSaveListRequest orderList,
                                       @AuthenticationPrincipal MemberPrincipal principal) {
        Long memberId = principal.getId();
        orderService.addOrders(orderList, memberId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 주문 굿즈 목록에서 주문 굿즈 삭제
     */
    @DeleteMapping("/{orderGoodsId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderGoodsId) {
        orderService.removeOrder(orderGoodsId);
        return ResponseEntity.noContent().build();
    }
}
