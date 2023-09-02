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

    @OneToOne(mappedBy = "delivery")
    private Orders orders;

    public static Delivery createDelivery(DeliverySaveRequest deliverySaveRequest) {
        return Delivery.builder()
                .deliveryNumber(deliverySaveRequest.getDeliveryNumber())
                .address(new Address(
                        deliverySaveRequest.getZipcode(),
                        deliverySaveRequest.getAddressDistrict(),
                        deliverySaveRequest.getAddressDetail()))
                .deliveryStatus(DeliveryStatus.DELIVERY_READY)
                .build();
    }

    public void addDelivery(Orders orders) {
        this.orders = orders;
    }
}
