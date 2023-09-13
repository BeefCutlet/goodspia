package shop.goodspia.goods.dto.delivery;

import lombok.Builder;
import lombok.Getter;
import shop.goodspia.goods.entity.DeliveryStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class DeliverySaveRequest {

    @NotBlank
    private String deliveryNumber;

    @NotBlank
    @Pattern(regexp = "\\d{3}-\\d{2}")
    private String zipcode;

    @NotBlank
    @Pattern(regexp = "([가-힣]\\s[0-9])")
    private String addressDistrict;

    @NotBlank
    @Pattern(regexp = "[가-힣0-9a-zA-Z]+")
    private String addressDetail;

    @NotNull
    private DeliveryStatus deliveryStatus;
}
