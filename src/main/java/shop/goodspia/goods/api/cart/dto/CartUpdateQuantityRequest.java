package shop.goodspia.goods.api.cart.dto;

import lombok.Getter;

import javax.validation.constraints.Min;

@Getter
public class CartUpdateQuantityRequest {

    @Min(1)
    private Long cartId;

    @Min(1)
    private int quantity;
}
