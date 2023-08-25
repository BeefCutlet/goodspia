package shop.goodspia.goods.service;

import shop.goodspia.goods.dto.payment.PaymentPrepareRequestDto;
import shop.goodspia.goods.dto.payment.PaymentPrepareResponseDto;
import shop.goodspia.goods.dto.payment.PaymentRequestDto;

public interface PaymentAgentService {

    PaymentPrepareResponseDto reservePayment(PaymentPrepareRequestDto paymentPrepareRequestDto)
            throws Exception;
    PaymentRequestDto validatePayment(String paymentUid) throws Exception;
}
