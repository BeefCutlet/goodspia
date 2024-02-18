package shop.goodspia.goods.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class OrderSaveRequest {

    @Min(1)
    @Max(1000)
    private int quantity;

    @Min(1000)
    @Max(100000000)
    private int totalPrice;

    @Min(1)
    private Long goodsId;

    @NotNull
    @Pattern(regexp = "[0-9a-zA-Z가-힣]")
    private String goodsDesign;
}
