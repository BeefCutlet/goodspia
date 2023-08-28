package shop.goodspia.goods.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class OrderRequestDto {

    @NotNull
    private int quantity;
    @NotNull
    private int totalPrice;
    @NotNull
    private Long goodsId;
    @NotNull
    private String goodsDesign;
}
