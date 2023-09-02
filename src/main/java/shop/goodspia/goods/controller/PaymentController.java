package shop.goodspia.goods.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "결제 정보 사전등록/검증 및 저장 API", description = "결제 정보를 사전 등록하고, 사전등록된 정보를 바탕으로 검증 후 저장하는 API")
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
    @Operation(summary = "결제 사전검증 API", description = "결제 진행 전 결제되어야 할 금액을 미리 저장하는 API")
    @PostMapping("/reservation")
    public ResponseEntity<PaymentPrepareResponse> reservePayment(@Parameter(name = "결제 정보", description = "추후 검증을 위해 사전에 등록할 결제 정보")
                                                                 @RequestBody @Valid PaymentPrepareRequest preparePaymentInfo)
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
    @Operation(summary = "결제 검증 API", description = "결제되어야 할 금액과 실제 결제된 금액이 일치하는지 유효성 검사를 하는 API")
    @PostMapping("/validation/{paymentUid}")
    public ResponseEntity<UrlResponse> validatePayment(@Parameter(name = "결제 UID", description = "결제 API 에서 결제 처리 후 생성된 결제 UID")
                                                       @PathVariable String paymentUid) throws Exception {
        PaymentRequest paymentRequest = paymentAgentService.validatePayment(paymentUid);
        paymentService.addPayment(paymentRequest);

        return ResponseEntity.ok(UrlResponse.of(baseUrl));
    }
}
