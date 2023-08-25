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
    private String zipcode;
    private String address1;
    private String address2;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @OneToOne(mappedBy = "delivery")
    private Orders orders;

    public static Delivery createDelivery(DeliveryRequestDto deliveryRequestDto) {
        return Delivery.builder()
                .deliveryNumber(deliveryRequestDto.getDeliveryNumber())
                .zipcode(deliveryRequestDto.getZipcode())
                .address1(deliveryRequestDto.getAddress1())
                .address2(deliveryRequestDto.getAddress2())
                .deliveryStatus(DeliveryStatus.DELIVERY_READY)
                .build();
    }

    public void addDelivery(Orders orders) {
        this.orders = orders;
    }
}
