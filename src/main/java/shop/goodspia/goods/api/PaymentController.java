package shop.goodspia.goods.api;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.dto.iamport.IAmPortRequestDto;
import shop.goodspia.goods.dto.iamport.IAmPortResponseDto;
import shop.goodspia.goods.dto.payment.PaymentRequestDto;
import shop.goodspia.goods.dto.payment.PaymentListRequestDto;
import shop.goodspia.goods.security.dto.SessionUser;
import shop.goodspia.goods.service.PaymentService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/payment")
@PropertySource(value = {"classpath:/secret.properties"})
public class PaymentController {

    private final IamportClient iamportClient;
    private final PaymentService paymentService;

    @Value("${iamport.rest-api.key}")
    private String restApiKey;
    @Value("${iamport.rest-api.secret-key}")
    private String restApiSecretKey;

    public PaymentController(PaymentService paymentService) {
        this.iamportClient = new IamportClient(restApiKey, restApiSecretKey);
        this.paymentService = paymentService;
    }

    /**
     * DB에 결제 정보 저장 (단건)
     * @param paymentRequestDto
     * @param session
     * @return
     */
    @PostMapping("/add")
    public String  addPayment(@RequestBody PaymentRequestDto paymentRequestDto, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        paymentService.addPayment(paymentRequestDto, 2);
        return "ok";
    }

    /**
     * DB에 결제 정보 저장 (리스트)
     * @param paymentList
     * @param session
     * @return
     */
    @PostMapping("/add-list")
    public String addPaymentList(@RequestBody PaymentListRequestDto paymentList, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        paymentService.addPaymentList(paymentList, 2);
        return "ok";
    }

    /**
     * 결제 금액 조작 검증을 위한 아임포트 결제 사전등록
     * @param iAmPortDto
     * @return
     */
    @PostMapping("/iamport-register")
    public ResponseEntity<IAmPortResponseDto> verifyPaymentsPrepare(@RequestBody @Valid IAmPortRequestDto iAmPortDto) {
        IAmPortResponseDto iAmPortResponseDto = paymentService.registerPaymentInfo(iAmPortDto);
        return ResponseEntity.ok(iAmPortResponseDto);
    }

    @PostMapping("/iamport-verify/{merchant_uid}")
    public IamportResponse<Payment> verifyPaymentsComplete(
            @PathVariable String merchant_uid,
            @RequestBody IAmPortRequestDto iAmPortRequestDto)
            throws IamportResponseException, IOException {
        paymentService.verifyPaymentInfo(merchant_uid, iAmPortRequestDto);
        return iamportClient.paymentByImpUid(iAmPortRequestDto.getMerchant_uid());
    }
}
