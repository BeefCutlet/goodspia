package shop.goodspia.goods.service;

import shop.goodspia.goods.dto.payment.PaymentPrepareRequest;
import shop.goodspia.goods.dto.payment.PaymentPrepareResponse;
import shop.goodspia.goods.dto.payment.PaymentRequest;

public interface PaymentAgentService {

    PaymentPrepareResponse reservePayment(PaymentPrepareRequest paymentPrepareRequest)
            throws Exception;
    PaymentRequest validatePayment(String paymentUid) throws Exception;
}
