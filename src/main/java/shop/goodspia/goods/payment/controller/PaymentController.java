package shop.goodspia.goods.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.common.dto.UrlResponse;
import shop.goodspia.goods.payment.dto.PaymentPrepareRequest;
import shop.goodspia.goods.payment.dto.PaymentPrepareResponse;
import shop.goodspia.goods.payment.dto.PaymentRequest;
import shop.goodspia.goods.payment.service.PaymentAgentService;
import shop.goodspia.goods.payment.service.PaymentService;

import javax.validation.Valid;
import java.net.URI;

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
     * @param preparePaymentInfo
     * @return
     * @throws Exception
     */
    @PostMapping("/reservation")
    public ResponseEntity<PaymentPrepareResponse> reservePayment(@RequestBody @Valid PaymentPrepareRequest preparePaymentInfo)
            throws Exception {
        PaymentPrepareResponse paymentPrepareResponse = paymentAgentService.reservePayment(preparePaymentInfo);

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
        //결제 정보 검증
        PaymentRequest paymentRequest = paymentAgentService.validatePayment(paymentUid);
        //검증 완료 시 결제 정보 및 배송지 정보를 DB에 저장
        paymentService.addPaymentAndDelivery(paymentRequest);

        return ResponseEntity.created(URI.create(baseUrl)).build();
    }
}
