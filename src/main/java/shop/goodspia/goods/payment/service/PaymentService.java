package shop.goodspia.goods.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.order.entity.Orders;
import shop.goodspia.goods.order.repository.OrderRepository;
import shop.goodspia.goods.payment.dto.PaymentRequest;
import shop.goodspia.goods.payment.entity.Payments;
import shop.goodspia.goods.payment.repository.PaymentRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    /**
     * 결제 정보 저장
     * @param paymentRequest
     * @return
     */
    public Long addPaymentAndDelivery(PaymentRequest paymentRequest) {
        //결제 정보 저장
        Payments payments = Payments.from(paymentRequest);
        Long paymentId = paymentRepository.save(payments).getId();
        //주문 정보와 결제 정보 연관관계 생성
        Orders order = orderRepository.findByOrderUid(paymentRequest.getOrderUid());
        order.addPayments(payments);

        return paymentId;
    }

}
