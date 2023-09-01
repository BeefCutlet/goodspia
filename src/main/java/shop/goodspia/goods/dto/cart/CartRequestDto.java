package shop.goodspia.goods.dto.cart;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class CartRequestDto {

    @NotNull
    @Min(1)
    private int quantity;
    @NotNull
    @Min(1)
    private long memberId;
    @NotNull
    @Min(1)
    private long goodsId;
    @NotNull
    @Min(1)
    private long designId;
}
