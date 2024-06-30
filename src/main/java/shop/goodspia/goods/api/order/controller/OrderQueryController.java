package shop.goodspia.goods.api.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.api.order.dto.OrderDetailResponse;
import shop.goodspia.goods.api.order.dto.OrderPageResponse;
import shop.goodspia.goods.api.order.dto.OrderReceivedResponse;
import shop.goodspia.goods.api.order.dto.OrderResponse;
import shop.goodspia.goods.api.order.service.OrderService;
import shop.goodspia.goods.global.security.dto.MemberPrincipal;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderQueryController {

    private final OrderService orderService;

    /**
     * 회원이 결제하지 않은 주문 목록 (주문 목록 페이지)
     */
    @GetMapping
    public ResponseEntity<OrderPageResponse<OrderResponse>> getOrderList(HttpServletRequest request, Pageable pageable) {
        Long memberId = (Long) request.getAttribute("memberId");
        OrderPageResponse<OrderResponse> orders = orderService.getRequestedOrders(memberId, pageable);
        return ResponseEntity.ok(orders);
    }

    /**
     * 아티스트에게 접수된 주문 목록
     */
    @GetMapping("/artist")
    public ResponseEntity<OrderPageResponse<OrderReceivedResponse>> getArtistOrderList(@RequestParam String year,
                                                                                       @RequestParam String month,
                                                                                       Pageable pageable,
                                                                                       @AuthenticationPrincipal MemberPrincipal principal) {
        Long memberId = principal.getId();
        OrderPageResponse<OrderReceivedResponse> receivedOrders = orderService.getReceivedOrders(memberId, pageable, year, month);
        return ResponseEntity.ok(receivedOrders);
    }

    /**
     * 회원이 주문한 주문 리스트 조회
     */
    @GetMapping("/member")
    public ResponseEntity<OrderPageResponse<OrderResponse>> getOrderCompleteList(Pageable pageable,
                                                                                 HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");
        OrderPageResponse<OrderResponse> completeOrders = orderService.getCompleteOrders(memberId, pageable);
        return ResponseEntity.ok(completeOrders);
    }

    /**
     * 회원이 주문한 주문 상세 정보 조회
     */
    @GetMapping("/detail/{orderGoodsId}")
    public ResponseEntity<OrderDetailResponse> getOrderDetail(@PathVariable Long orderGoodsId) {
        OrderDetailResponse orderDetail = orderService.getOrderDetail(orderGoodsId);
        return ResponseEntity.ok(orderDetail);
    }
}
