package shop.goodspia.goods.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.dto.payment.PaymentRequestDto;
import shop.goodspia.goods.entity.Payments;
import shop.goodspia.goods.repository.PaymentRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Long addPayment(PaymentRequestDto paymentRequestDto) {
        Payments payments = Payments.createPayments(paymentRequestDto);
        return paymentRepository.save(payments).getId();
    }
}
