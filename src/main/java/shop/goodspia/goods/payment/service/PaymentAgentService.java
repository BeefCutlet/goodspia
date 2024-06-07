package shop.goodspia.goods.payment.service;

import shop.goodspia.goods.payment.dto.PaymentPrepareRequest;
import shop.goodspia.goods.payment.dto.PaymentPrepareResponse;
import shop.goodspia.goods.payment.dto.PaymentRequest;

public interface PaymentAgentService {

    PaymentPrepareResponse reservePayment(PaymentPrepareRequest paymentPrepareRequest)
            throws Exception;
    Long completePayment(PaymentRequest paymentRequest) throws Exception;
}
