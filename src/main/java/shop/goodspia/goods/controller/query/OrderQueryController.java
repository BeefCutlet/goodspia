package shop.goodspia.goods.controller.query;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.dto.order.OrderDetailResponse;
import shop.goodspia.goods.dto.order.OrderReceivedResponse;
import shop.goodspia.goods.dto.order.OrderResponse;
import shop.goodspia.goods.security.dto.SessionUser;
import shop.goodspia.goods.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrderList(@Parameter(hidden = true) HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");
        List<OrderResponse> orders = orderService.getRequestedOrders(memberId);
        return ResponseEntity.ok(orders);
    }

    /**
     * 아티스트에게 접수된 주문 목록
     * @param pageable
     * @param request
     * @return
     */
    @Operation(summary = "아티스트의 주문 목록 조회 API",
            description = "현재 접속 중인 아티스트에게 접수된 주문 목록을 조회합니다. pageSize와 pageNum을 파라미터로 설정합니다.")
    @GetMapping("/artist")
    public ResponseEntity<Page<OrderReceivedResponse>> getArtistOrderList(@Parameter(hidden = true) Pageable pageable,
                                                                          @Parameter(hidden = true) HttpServletRequest request) {
        Long artistId = (Long) request.getAttribute("artistId");
        Page<OrderReceivedResponse> receivedOrders = orderService.getReceivedOrders(artistId, pageable);
        return ResponseEntity.ok(receivedOrders);
    }

    /**
     * 회원이 주문한 주문 리스트 조회
     * @param pageable
     * @param session
     * @return
     */
    @Operation(summary = "회원이 주문한 주문 목록 조회 API", description = "현재 접속 중인 회원이 등록한 주문 목록을 조회합니다. pageSize와 pageNum을 파라미터로 설정합니다.")
    @GetMapping("/member")
    public ResponseEntity<Page<OrderResponse>> getOrderCompleteList(@Parameter(hidden = true) Pageable pageable,
                                                                    @Parameter(hidden = true) HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        Page<OrderResponse> completeOrders = orderService.getCompleteOrders(user.getMemberId(), pageable);
        return ResponseEntity.ok(completeOrders);
    }

    /**
     * 회원이 주문한 주문 상세 정보 조회
     * @param orderGoodsId
     * @return
     */
    @Operation(summary = "회원이 주문한 주문 상세 정보 조회 API", description = "주문 상품의 번호를 파라미터로 설정합니다.")
    @GetMapping("/detail/{orderGoodsId}")
    public ResponseEntity<OrderDetailResponse> getOrderDetail(@PathVariable Long orderGoodsId) {
        OrderDetailResponse orderDetail = orderService.getOrderDetail(orderGoodsId);
        return ResponseEntity.ok(orderDetail);
    }
}
