package shop.goodspia.goods.entity;

import lombok.*;
import shop.goodspia.goods.dto.delivery.DeliveryRequestDto;

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

    public static Delivery createDelivery(DeliveryRequestDto deliveryRequestDto) {
        return Delivery.builder()
                .deliveryNumber(deliveryRequestDto.getDeliveryNumber())
                .address(new Address(
                        deliveryRequestDto.getZipcode(),
                        deliveryRequestDto.getAddressDistrict(),
                        deliveryRequestDto.getAddressDetail()))
                .deliveryStatus(DeliveryStatus.DELIVERY_READY)
                .build();
    }

    public void addDelivery(Orders orders) {
        this.orders = orders;
    }
}
