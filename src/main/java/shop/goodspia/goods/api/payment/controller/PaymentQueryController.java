package shop.goodspia.goods.api.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.api.payment.dto.PaymentResponse;
import shop.goodspia.goods.api.payment.service.PaymentService;
import shop.goodspia.goods.global.security.dto.MemberPrincipal;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentQueryController {

    private final PaymentService paymentService;

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable Long paymentId,
                                                      @AuthenticationPrincipal MemberPrincipal principal) {
        Long memberId = principal.getId();
        PaymentResponse paymentResponse = paymentService.getPayment(memberId, paymentId);
        return ResponseEntity.ok(paymentResponse);
    }
}
