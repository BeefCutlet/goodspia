package shop.goodspia.goods.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
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
}
