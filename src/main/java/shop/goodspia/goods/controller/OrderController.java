package shop.goodspia.goods.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.dto.Response;
import shop.goodspia.goods.dto.UrlResponse;
import shop.goodspia.goods.dto.order.OrderListRequestDto;
import shop.goodspia.goods.service.OrderService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@PropertySource(value = {"classpath:/secret.properties"})
public class OrderController {

    private final OrderService orderService;

    @Value("${base-url}")
    private String baseUrl;

    /**
     * DB에 주문 정보 저장
     * 굿즈 상세페이지의 결제하기 버튼, 장바구니의 결제하기 버튼 클릭 시 동작
     * @param orderList
     * @param authorization
     * @return 주문 목록 페이지로 이동 URL
     */
    @PostMapping
    public Response<UrlResponse> addOrders(@RequestBody @Valid OrderListRequestDto orderList,
                                           @RequestHeader("Authorization") String authorization) {

        orderService.addOrders(orderList, 2L);
        return Response.of(HttpStatus.OK.value(), "", UrlResponse.of(baseUrl));
    }

    /**
     * 주문 굿즈 목록에서 주문 굿즈 삭제
     * @param orderGoodsId
     * @return
     */
    @DeleteMapping("/{orderGoodsId}")
    public Response<?> deleteOrder(@PathVariable Long orderGoodsId) {
        orderService.removeOrder(orderGoodsId);
        return Response.of(HttpStatus.OK.value(), "SUCCESS", null);
    }
}
