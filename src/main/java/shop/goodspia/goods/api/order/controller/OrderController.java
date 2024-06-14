package shop.goodspia.goods.api.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.api.order.dto.OrderSaveListRequest;
import shop.goodspia.goods.api.order.dto.OrderSaveResponse;
import shop.goodspia.goods.api.order.service.OrderService;
import shop.goodspia.goods.global.security.dto.MemberPrincipal;

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
    public ResponseEntity<OrderSaveResponse> addOrders(@RequestBody @Valid OrderSaveListRequest orderList,
                                                       @AuthenticationPrincipal MemberPrincipal principal) {
        Long memberId = principal.getId();
        OrderSaveResponse orderSaveResponse = orderService.addOrders(orderList, memberId);
        return ResponseEntity.ok(orderSaveResponse);
    }

    /**
     * 주문 굿즈 목록에서 주문 굿즈 삭제
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        orderService.removeOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
