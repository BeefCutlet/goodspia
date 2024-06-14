package shop.goodspia.goods.api.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.api.order.repository.OrderRepository;
import shop.goodspia.goods.api.payment.repository.PaymentRepository;
import shop.goodspia.goods.api.payment.dto.PaymentResponse;
import shop.goodspia.goods.api.payment.entity.Payments;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    /**
     * 사용자가 결제한 결제 정보 단건 조회
     */
    public PaymentResponse getPayment(Long memberId, Long paymentId) {
        Payments foundPayment = paymentRepository.findPaymentsById(paymentId).orElseThrow(() -> {
            throw new IllegalArgumentException("결제 정보가 존재하지 않습니다.");
        });

        if (!foundPayment.getOrders().getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("결제 당사자가 아닙니다.");
        }

        return PaymentResponse.from(foundPayment);
    }
}
