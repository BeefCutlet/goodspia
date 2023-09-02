package shop.goodspia.goods.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class OrderSaveRequest {

    @NotNull
    @Min(1)
    private int quantity;
    @NotNull
    @Min(1000)
    private int totalPrice;
    @NotNull
    @Min(1)
    private Long goodsId;
    @NotBlank
    private String goodsDesign;
}
