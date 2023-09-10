package shop.goodspia.goods.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.dto.order.OrderSaveListRequest;
import shop.goodspia.goods.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@Tag(name = "주문 등록/삭제 API", description = "새로운 주문을 등록, 기존의 주문을 삭제 처리하는 API")
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
     * @param request
     * @return 주문 목록 페이지로 이동 URL
     */
    @Operation(summary = "주문 등록 API", description = "현재 접속 중인 회원으로 주문 정보가 등록됩니다.")
    @PostMapping
    public ResponseEntity<?> addOrders(@Parameter(name = "주문 정보", description = "저장할 주문들의 정보")
                                       @RequestBody @Valid OrderSaveListRequest orderList,
                                       @Parameter(hidden = true) HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");
        orderService.addOrders(orderList, memberId);
        return ResponseEntity.created(URI.create(baseUrl)).build();
    }

    /**
     * 주문 굿즈 목록에서 주문 굿즈 삭제
     * @param orderGoodsId
     * @return
     */
    @Operation(summary = "주문 삭제 API", description = "기존의 주문을 삭제 처리하는 API")
    @DeleteMapping("/{orderGoodsId}")
    public ResponseEntity<String> deleteOrder(@Parameter(description = "삭제 처리할 주문의 번호") @PathVariable Long orderGoodsId) {
        orderService.removeOrder(orderGoodsId);
        return ResponseEntity.ok("");
    }
}
