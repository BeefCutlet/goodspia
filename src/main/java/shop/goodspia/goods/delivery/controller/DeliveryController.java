package shop.goodspia.goods.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.delivery.service.DeliveryService;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Value("${base-url}")
    private String baseUrl;

    /**
     * 굿즈 주문 결제 후 배송 정보 등록을 위한 API
     * 배송 API로 배송 정보 등록 후 주문 데이터에 연관관계 설정
     */
    @PutMapping("/{orderId}")
    public ResponseEntity<?> registerDelivery(@PathVariable Long orderId) {

        return ResponseEntity.created(URI.create(baseUrl)).build();
    }
}
