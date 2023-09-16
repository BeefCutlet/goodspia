package shop.goodspia.goods.delivery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.delivery.dto.DeliverySaveRequest;
import shop.goodspia.goods.delivery.entity.Delivery;
import shop.goodspia.goods.delivery.dto.DeliveryStatus;
import shop.goodspia.goods.delivery.repository.DeliveryRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    //배송 상태 변경 메서드
    public void changeStatus(long orderId, DeliverySaveRequest deliverySaveRequest) {
        Delivery delivery = deliveryRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("배송 정보가 존재하지 않습니다."));

        delivery.changeDeliveryStatus(DeliveryStatus.convertToDeliveryStatus(deliverySaveRequest.getDeliveryStatus()));
    }
}
