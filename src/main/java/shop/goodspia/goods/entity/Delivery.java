package shop.goodspia.goods.entity;

import lombok.*;
import shop.goodspia.goods.dto.DeliveryDto;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "shipment_id")
    private Long id;
    private String deliveryNumber;
    private String zipcode;
    private String address1;
    private String address2;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    public static Delivery createDelivery(DeliveryDto deliveryDto) {
        return Delivery.builder()
                .deliveryNumber(deliveryDto.getDeliveryNumber())
                .zipcode(deliveryDto.getZipcode())
                .address1(deliveryDto.getAddress1())
                .address2(deliveryDto.getAddress2())
                .deliveryStatus(DeliveryStatus.convertToDeliveryStatus(deliveryDto.getDeliveryStatus()))
                .build();
    }
}
