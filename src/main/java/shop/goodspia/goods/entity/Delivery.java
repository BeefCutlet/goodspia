package shop.goodspia.goods.entity;

import lombok.*;
import shop.goodspia.goods.dto.delivery.DeliverySaveRequest;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;
    private String deliveryNumber;
    private Address address;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @OneToOne
    @JoinColumn(name = "orders_id")
    private Orders orders;

    public static Delivery createDelivery(DeliverySaveRequest deliverySaveRequest, Orders order) {
        return Delivery.builder()
                .deliveryNumber(deliverySaveRequest.getDeliveryNumber())
                .address(new Address(
                        deliverySaveRequest.getZipcode(),
                        deliverySaveRequest.getAddressDistrict(),
                        deliverySaveRequest.getAddressDetail()))
                .deliveryStatus(DeliveryStatus.PRODUCT_READY)
                .orders(order)
                .build();
    }

    //배송 상태 변경용 메서드
    public void changeDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
