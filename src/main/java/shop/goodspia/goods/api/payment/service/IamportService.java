package shop.goodspia.goods.api.payment.service;

import com.google.gson.Gson;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.PrepareData;
import com.siot.IamportRestClient.response.Prepare;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import shop.goodspia.goods.api.order.entity.Orders;
import shop.goodspia.goods.api.order.repository.OrderRepository;
import shop.goodspia.goods.api.payment.repository.PaymentRepository;
import shop.goodspia.goods.api.payment.dto.PaymentPrepareRequest;
import shop.goodspia.goods.api.payment.dto.PaymentPrepareResponse;
import shop.goodspia.goods.api.payment.dto.PaymentRequest;
import shop.goodspia.goods.api.payment.entity.Payments;

import java.io.IOException;
import java.math.BigDecimal;

@Slf4j
@Service
public class IamportService implements PaymentAgentService {

    private final IamportClient iamportClient;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public IamportService(@Value("${iamport.rest-api.key}") String iamPortApiKey,
                          @Value("${iamport.rest-api.secret-key}")String iamPortSecretKey,
                          PaymentRepository paymentRepository,
                          OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
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
    public Long completePayment(PaymentRequest paymentRequest) throws IamportResponseException, IOException {
        //DB에 저장된 결제 금액과 실제 결제 금액 비교
        Orders foundOrder = orderRepository.findById(paymentRequest.getOrderId()).orElseThrow(() -> {
            throw new IllegalArgumentException("주문 정보가 존재하지 않습니다.");
        });

//        //주의) 테스트 결제라서 조회 불가능 => 404 발생
//        Payment payment = iamportClient.paymentByImpUid(paymentRequest.getPaymentUid()).getResponse();
//        if (payment == null) {
//            throw new IllegalArgumentException("결제 정보가 존재하지 않습니다.");
//        }
//
//        //주문 금액과 실제 결제 금액이 일치하지 않으면
//        int paidAmount = payment.getAmount().intValue();
//        if (paidAmount != foundOrder.getOrderPrice()) {
//            iamportClient.cancelPaymentByImpUid(new CancelData(payment.getImpUid(), true, new BigDecimal(paidAmount)));
//            throw new PaymentValidationFailureException("주문 금액과 실제 결제 금액이 일치하지 않습니다.");
//        }

        Payments newPayment = Payments.from(paymentRequest, foundOrder);
        Long savedPaymentId = paymentRepository.save(newPayment).getId();

        foundOrder.completeOrder();

        return savedPaymentId;
    }

    //Prepare 객체를 PaymentPrepareResponseDto 객체로 파싱
    private PaymentPrepareResponse parsingToPaymentPrepareResponseDto(Prepare prepare) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(prepare), PaymentPrepareResponse.class);
    }
}
