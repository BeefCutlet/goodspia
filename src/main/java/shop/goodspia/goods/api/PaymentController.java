package shop.goodspia.goods.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.dto.Response;
import shop.goodspia.goods.dto.UrlResponse;
import shop.goodspia.goods.dto.payment.PaymentPrepareRequestDto;
import shop.goodspia.goods.dto.payment.PaymentPrepareResponseDto;
import shop.goodspia.goods.dto.payment.PaymentRequestDto;
import shop.goodspia.goods.service.PaymentAgentService;
import shop.goodspia.goods.service.PaymentService;

@Slf4j
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentAgentService paymentAgentService;
    private final PaymentService paymentService;

    @Value("${base-url}")
    private String baseUrl;

    @PostMapping("/reservation")
    public Response<PaymentPrepareResponseDto> reservePayment(@RequestBody PaymentPrepareRequestDto prepareDto)
            throws Exception {
        PaymentPrepareResponseDto paymentPrepareResponseDto = paymentAgentService.reservePayment(prepareDto);

        return Response.of(HttpStatus.OK.value(), "", paymentPrepareResponseDto);
    }

    @PostMapping("/validation/{paymentUid}")
    public Response<UrlResponse> validatePayment(@PathVariable String paymentUid) throws Exception {
        PaymentRequestDto paymentRequestDto = paymentAgentService.validatePayment(paymentUid);
        paymentService.addPayment(paymentRequestDto);

        return Response.of(HttpStatus.OK.value(), "결제를 성공하였습니다.", UrlResponse.of(baseUrl));
    }
}
