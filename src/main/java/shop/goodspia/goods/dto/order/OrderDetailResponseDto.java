package shop.goodspia.goods.dto.order;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDetailResponseDto {

    private String orderUid;
    private String createdTime;
    private Long goodsId;
    private String goodsName;
    private String goodsSummary;
    private String goodsImage;
    private String deliveryNumber;
    private String deliveryStatus;
    private String deliveryZipcode;
    private String deliveryAddressDistrict;
    private String deliveryAddressDetail;
}
