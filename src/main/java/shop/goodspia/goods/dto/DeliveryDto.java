package shop.goodspia.goods.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter @Setter
public class DeliveryDto {

    private String deliveryNumber;
    private String zipcode;
    private String address1;
    private String address2;
    @Enumerated(EnumType.STRING)
    private String deliveryStatus;
}
