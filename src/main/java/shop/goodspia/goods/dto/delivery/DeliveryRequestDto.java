package shop.goodspia.goods.dto.delivery;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Builder
public class DeliveryRequestDto {

    private String deliveryNumber;
    private String zipcode;
    private String addressDistrict;
    private String addressDetail;
    @Enumerated(EnumType.STRING)
    private String deliveryStatus;
}
