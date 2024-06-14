package shop.goodspia.goods.api.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.api.payment.dto.PaymentRequest;
import shop.goodspia.goods.api.payment.service.PaymentAgentService;
import shop.goodspia.goods.api.payment.service.PaymentService;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentAgentService paymentAgentService;
    private final PaymentService paymentService;

    @Value("${base-url}")
    private String baseUrl;

    @PostMapping("/complete")
    public ResponseEntity<Void> completePayment(@RequestBody @Valid PaymentRequest paymentRequest) throws Exception {
        Long savedPaymentId = paymentAgentService.completePayment(paymentRequest);
        return ResponseEntity.created(URI.create("/payment/" + savedPaymentId)).build();
    }
}
