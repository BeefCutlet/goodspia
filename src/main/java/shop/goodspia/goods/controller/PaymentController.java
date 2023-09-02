package shop.goodspia.goods.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.dto.UrlResponse;
import shop.goodspia.goods.dto.payment.PaymentPrepareRequest;
import shop.goodspia.goods.dto.payment.PaymentPrepareResponse;
import shop.goodspia.goods.dto.payment.PaymentRequest;
import shop.goodspia.goods.service.PaymentAgentService;
import shop.goodspia.goods.service.PaymentService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentAgentService paymentAgentService;
    private final PaymentService paymentService;

    @Value("${base-url}")
    private String baseUrl;

    /**
     * 결제 전 결제 정보 사전등록
     * @param prepareDto
     * @return
     * @throws Exception
     */
    @PostMapping("/reservation")
    public ResponseEntity<PaymentPrepareResponse> reservePayment(@RequestBody @Valid PaymentPrepareRequest prepareDto)
            throws Exception {
        PaymentPrepareResponse paymentPrepareResponse = paymentAgentService.reservePayment(prepareDto);

        return ResponseEntity.ok(paymentPrepareResponse);
    }

    /**
     * 결제 후 금액 검증
     * @param paymentUid
     * @return
     * @throws Exception
     */
    @PostMapping("/validation/{paymentUid}")
    public ResponseEntity<UrlResponse> validatePayment(@PathVariable String paymentUid) throws Exception {
        PaymentRequest paymentRequest = paymentAgentService.validatePayment(paymentUid);
        paymentService.addPayment(paymentRequest);

        return ResponseEntity.ok(UrlResponse.of(baseUrl));
    }
}
