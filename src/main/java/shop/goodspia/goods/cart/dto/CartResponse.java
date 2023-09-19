package shop.goodspia.goods.cart.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class CartResponse {

    @Min(1)
    @Max(1000)
    private int quantity;

    @NotNull
    @Pattern(regexp = "[0-9a-zA-Z가-힣]")
    @Size(min = 1, max = 50)
    private String goodsName;

    @NotNull
    @Pattern(regexp = "[0-9a-zA-Z가-힣]")
    private String designName;

    @Min(1000)
    @Max(10000000)
    private int price;

    private String thumbnail;
}
