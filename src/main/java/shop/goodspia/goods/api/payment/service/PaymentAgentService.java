package shop.goodspia.goods.api.payment.service;

import shop.goodspia.goods.api.payment.dto.PaymentPrepareRequest;
import shop.goodspia.goods.api.payment.dto.PaymentPrepareResponse;
import shop.goodspia.goods.api.payment.dto.PaymentRequest;

public interface PaymentAgentService {

    PaymentPrepareResponse reservePayment(PaymentPrepareRequest paymentPrepareRequest)
            throws Exception;
    Long completePayment(PaymentRequest paymentRequest) throws Exception;
}
