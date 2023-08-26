package shop.goodspia.goods.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.dto.Response;
import shop.goodspia.goods.dto.UrlResponse;
import shop.goodspia.goods.dto.order.OrderListRequestDto;
import shop.goodspia.goods.security.dto.SessionUser;
import shop.goodspia.goods.service.OrderService;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/order")
@PropertySource(value = {"classpath:/secret.properties"})
public class OrderController {

    private final OrderService orderService;

    @Value("${base-url}")
    private String baseUrl;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * DB에 주문 정보 저장
     * @param orderList
     * @param session
     * @return
     */
    @PostMapping("/list")
    public Response<UrlResponse> addOrders(@RequestBody OrderListRequestDto orderList, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        orderService.addOrders(orderList, 2);
        return Response.of(HttpStatus.OK.value(), "", UrlResponse.of(baseUrl));
    }

    @DeleteMapping("/one/{orderId}")
    public Response<?> deleteOrder(@PathVariable long orderId) {
        orderService.removeOrder(orderId);
        return Response.of(HttpStatus.OK.value(), "SUCCESS", null);
    }
}
