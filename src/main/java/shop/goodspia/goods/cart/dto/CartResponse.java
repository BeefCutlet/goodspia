package shop.goodspia.goods.cart.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartResponse {

    @Min(1)
    private int quantity;
    @NotNull
    private String goodsName;
    @NotNull
    private String designName;
    @Min(1000)
    @Max(10000000)
    private int price;
    @NotNull
    private String thumbnail;
}
