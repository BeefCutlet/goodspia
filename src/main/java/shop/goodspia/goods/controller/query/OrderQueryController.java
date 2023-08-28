package shop.goodspia.goods.controller.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.dto.Response;
import shop.goodspia.goods.dto.order.OrderDetailResponseDto;
import shop.goodspia.goods.dto.order.OrderReceivedResponseDto;
import shop.goodspia.goods.dto.order.OrderResponseDto;
import shop.goodspia.goods.security.dto.SessionUser;
import shop.goodspia.goods.service.OrderService;

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
     * @param session
     * @return
     */
    @GetMapping
    public Response<List<OrderResponseDto>> getOrderList(HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        List<OrderResponseDto> orders = orderService.getRequestedOrders(user.getMemberId());
        return Response.of(HttpStatus.OK.value(), "회원이 결제하지 않은 주문 리스트 조회 성공", orders);
    }

    /**
     * 아티스트에게 접수된 주문 목록
     * @param pageable
     * @param session
     * @return
     */
    @GetMapping("/artist")
    public Response<Page<OrderReceivedResponseDto>> getArtistOrderList(Pageable pageable, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        Page<OrderReceivedResponseDto> receivedOrders = orderService.getReceivedOrders(user.getArtistId(), pageable);
        return Response.of(HttpStatus.OK.value(), "아티스트에게 접수된 주문 리스트 조회 성공", receivedOrders);
    }

    /**
     * 회원이 주문한 주문 리스트 조회
     * @param pageable
     * @param session
     * @return
     */
    @GetMapping("/member")
    public Response<Page<OrderResponseDto>> getOrderCompleteList(Pageable pageable, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        Page<OrderResponseDto> completeOrders = orderService.getCompleteOrders(user.getMemberId(), pageable);
        return Response.of(HttpStatus.OK.value(), "회원이 주문한 주문 리스트 조회 성공", completeOrders);
    }

    /**
     * 회원이 주문한 주문 상세 정보 조회
     * @param orderGoodsId
     * @return
     */
    @GetMapping("/detail/{orderGoodsId}")
    public Response<OrderDetailResponseDto> getOrderDetail(@PathVariable Long orderGoodsId) {
        OrderDetailResponseDto orderDetail = orderService.getOrderDetail(orderGoodsId);
        return Response.of(HttpStatus.OK.value(), "회원이 주문한 주문의 상세 정보 조회 성공", orderDetail);
    }
}
