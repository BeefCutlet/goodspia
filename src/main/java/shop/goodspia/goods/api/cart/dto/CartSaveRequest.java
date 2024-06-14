package shop.goodspia.goods.api.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@AllArgsConstructor
public class CartSaveRequest {

    @Min(1)
    @Max(1000)
    private int quantity;

    @Min(1)
    private Long goodsId;

    @Min(1)
    private Long designId;
}
