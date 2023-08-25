package shop.goodspia.goods.service;

import com.google.gson.Gson;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.PrepareData;
import com.siot.IamportRestClient.response.Payment;
import com.siot.IamportRestClient.response.Prepare;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import shop.goodspia.goods.dto.payment.PaymentPrepareRequestDto;
import shop.goodspia.goods.dto.payment.PaymentPrepareResponseDto;
import shop.goodspia.goods.dto.payment.PaymentRequestDto;
import shop.goodspia.goods.repository.OrderQueryRepository;

import java.io.IOException;

@Slf4j
@Service
@PropertySource(value = {"classpath:/secret.properties"})
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
    public PaymentPrepareResponseDto reservePayment(PaymentPrepareRequestDto prepareDto)
            throws IamportResponseException, IOException {

        //아임포트 API로 주문번호과 결제금액 사전등록
        Prepare prepare = iamportClient.postPrepare(
                new PrepareData(prepareDto.getMerchant_uid(), prepareDto.getAmount())).getResponse();

        return parsingToPaymentPrepareResponseDto(prepare);
    }

    @Override
    public PaymentRequestDto validatePayment(String paymentUid) throws IamportResponseException, IOException {
        //DB에 저장된 결제 금액과 실제 결제 금액 비교
        Long totalPrice = orderQueryRepository.findTotalPrice(paymentUid);
        Payment payment = iamportClient.paymentByImpUid(paymentUid).getResponse();
        if (totalPrice != payment.getAmount().longValue()) {
            throw new IllegalStateException("주문 상품들의 결제 금액과 실제 결제 금액이 일치하지 않습니다.");
        }

        //사전등록된 결제 금액과 실제 결제 금액 비교
        Prepare prepare = iamportClient.getPrepare(payment.getMerchantUid()).getResponse();
        PaymentPrepareResponseDto prepareDto = parsingToPaymentPrepareResponseDto(prepare);
        if (payment.getAmount().intValue() != prepareDto.getAmount()) {
            throw new IllegalStateException("사전등록된 결제 금액과 실제 결제 금액이 일치하지 않습니다.");
        }

        return PaymentRequestDto.builder()
                .paymentUid(payment.getImpUid())
                .orderUid(payment.getMerchantUid())
                .cardBank(payment.getBankCode())
                .cardNumber(payment.getCardNumber())
                .amount(payment.getAmount().intValue())
                .build();
    }

    //Prepare 객체를 PaymentPrepareResponseDto 객체로 파싱
    private PaymentPrepareResponseDto parsingToPaymentPrepareResponseDto(Prepare prepare) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(prepare), PaymentPrepareResponseDto.class);
    }
}
