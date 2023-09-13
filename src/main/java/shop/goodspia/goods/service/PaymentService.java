package shop.goodspia.goods.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.dto.delivery.DeliverySaveRequest;
import shop.goodspia.goods.dto.payment.PaymentRequest;
import shop.goodspia.goods.entity.Delivery;
import shop.goodspia.goods.entity.Orders;
import shop.goodspia.goods.entity.Payments;
import shop.goodspia.goods.repository.DeliveryRepository;
import shop.goodspia.goods.repository.OrderRepository;
import shop.goodspia.goods.repository.PaymentRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;

    /**
     * 결제 정보 저장
     * @param paymentRequest
     * @return
     */
    public Long addPaymentAndDelivery(PaymentRequest paymentRequest, DeliverySaveRequest deliverySaveRequest) {
        //결제 정보 저장
        Payments payments = Payments.createPayments(paymentRequest);
        Long paymentId = paymentRepository.save(payments).getId();
        //주문 정보와 결제 정보 연관관계 생성
        Orders order = orderRepository.findByOrderUid(paymentRequest.getOrderUid());
        order.addPayments(payments);

        //배송 정보 추가 (상품 준비중 상태)
        Delivery delivery = Delivery.createDelivery(deliverySaveRequest, order);
        deliveryRepository.save(delivery);

        return paymentId;
    }

}
