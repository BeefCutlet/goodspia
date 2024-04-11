package shop.goodspia.goods.payment.service;

import com.google.gson.Gson;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.PrepareData;
import com.siot.IamportRestClient.response.Payment;
import com.siot.IamportRestClient.response.Prepare;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import shop.goodspia.goods.payment.dto.PaymentPrepareRequest;
import shop.goodspia.goods.payment.dto.PaymentPrepareResponse;
import shop.goodspia.goods.payment.dto.PaymentRequest;
import shop.goodspia.goods.common.exception.PaymentValidationFailureException;
import shop.goodspia.goods.order.repository.OrderQueryRepository;

import java.io.IOException;
import java.math.BigDecimal;

@Slf4j
@Service
public class IamportService implements PaymentAgentService {

    private final IamportClient iamportClient;
    private final OrderQueryRepository orderQueryRepository;

    public IamportService(@Value("${iamport.rest-api.key}") String iamPortApiKey,
                          @Value("${iamport.rest-api.secret-key}")String iamPortSecretKey,
                          OrderQueryRepository orderQueryRepository) {
        this.orderQueryRepository = orderQueryRepository;
        this.iamportClient = new IamportClient(iamPortApiKey, iamPortSecretKey, true);
    }

    @Override
    public PaymentPrepareResponse reservePayment(PaymentPrepareRequest prepareDto)
            throws IamportResponseException, IOException {

        //아임포트 API로 주문번호과 결제금액 사전등록
        Prepare prepare = iamportClient.postPrepare(
                new PrepareData(prepareDto.getMerchantUid(), BigDecimal.valueOf(prepareDto.getAmount()))).getResponse();

        return parsingToPaymentPrepareResponseDto(prepare);
    }

    @Override
    public PaymentRequest validatePayment(String paymentUid) throws IamportResponseException, IOException {
        //DB에 저장된 결제 금액과 실제 결제 금액 비교
        Integer totalPrice = orderQueryRepository.findTotalPrice(paymentUid);
        Payment payment = iamportClient.paymentByImpUid(paymentUid).getResponse();
        if (totalPrice != payment.getAmount().longValue()) {
            throw new PaymentValidationFailureException("주문 상품들의 결제 금액과 실제 결제 금액이 일치하지 않습니다.");
        }

        //사전등록된 결제 금액과 실제 결제 금액 비교
        Prepare prepare = iamportClient.getPrepare(payment.getMerchantUid()).getResponse();
        PaymentPrepareResponse prepareDto = parsingToPaymentPrepareResponseDto(prepare);
        if (payment.getAmount().intValue() != prepareDto.getAmount()) {
            throw new PaymentValidationFailureException("사전등록된 결제 금액과 실제 결제 금액이 일치하지 않습니다.");
        }

        return PaymentRequest.builder()
                .paymentUid(payment.getImpUid())
                .orderUid(payment.getMerchantUid())
                .cardBank(payment.getBankCode())
                .cardNumber(payment.getCardNumber())
                .amount(payment.getAmount().intValue())
                .build();
    }

    //Prepare 객체를 PaymentPrepareResponseDto 객체로 파싱
    private PaymentPrepareResponse parsingToPaymentPrepareResponseDto(Prepare prepare) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(prepare), PaymentPrepareResponse.class);
    }
}
