package shop.goodspia.goods.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.order.dto.OrderDetailResponse;
import shop.goodspia.goods.order.dto.OrderPageResponse;
import shop.goodspia.goods.order.dto.OrderReceivedResponse;
import shop.goodspia.goods.order.dto.OrderResponse;
import shop.goodspia.goods.order.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "주문 조회 API", description = "주문 목록을 조회하는 API")
@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderQueryController {

    private final OrderService orderService;

    /**
     * 회원이 결제하지 않은 주문 목록 (주문 목록 페이지)
     * @param request
     * @return
     */
    @Operation(summary = "현재 접속 중인 회원이 등록 및 미결제 상태의 주문 목록 조회 API")
    @GetMapping
    public ResponseEntity<OrderPageResponse<OrderResponse>> getOrderList(
            @Parameter(hidden = true) HttpServletRequest request,
            @Parameter(hidden = true) Pageable pageable) {
        Long memberId = (Long) request.getAttribute("memberId");
        OrderPageResponse<OrderResponse> orders = orderService.getRequestedOrders(memberId, pageable);
        return ResponseEntity.ok(orders);
    }

    /**
     * 아티스트에게 접수된 주문 목록
     * @param pageable
     * @param request
     * @return
     */
    @Operation(summary = "현재 접속 중인 아티스트에게 접수된 주문 목록 조회 API", description = "page와 size를 파라미터로 설정할 수 있습니다.")
    @GetMapping("/artist")
    public ResponseEntity<OrderPageResponse<OrderReceivedResponse>> getArtistOrderList(@Parameter(hidden = true) Pageable pageable,
                                                                          @Parameter(hidden = true) HttpServletRequest request) {
        Long artistId = (Long) request.getAttribute("artistId");
        OrderPageResponse<OrderReceivedResponse> receivedOrders = orderService.getReceivedOrders(artistId, pageable);
        return ResponseEntity.ok(receivedOrders);
    }

    /**
     * 회원이 주문한 주문 리스트 조회
     * @param pageable
     * @param request
     * @return
     */
    @Operation(summary = "현재 접속 중인 회원이 주문한 주문 목록 조회 API", description = "page와 size를 파라미터로 설정할 수 있습니다.")
    @GetMapping("/member")
    public ResponseEntity<OrderPageResponse<OrderResponse>> getOrderCompleteList(@Parameter(hidden = true) Pageable pageable,
                                                                                 @Parameter(hidden = true) HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");
        OrderPageResponse<OrderResponse> completeOrders = orderService.getCompleteOrders(memberId, pageable);
        return ResponseEntity.ok(completeOrders);
    }

    /**
     * 회원이 주문한 주문 상세 정보 조회
     * @param orderGoodsId
     * @return
     */
    @Operation(summary = "회원이 주문한 주문 상세 정보 조회 API", description = "주문 상품의 번호를 파라미터로 설정합니다.")
    @GetMapping("/detail/{orderGoodsId}")
    public ResponseEntity<OrderDetailResponse> getOrderDetail(@Parameter(description = "상품 주문 번호") @PathVariable Long orderGoodsId) {
        OrderDetailResponse orderDetail = orderService.getOrderDetail(orderGoodsId);
        return ResponseEntity.ok(orderDetail);
    }
}
