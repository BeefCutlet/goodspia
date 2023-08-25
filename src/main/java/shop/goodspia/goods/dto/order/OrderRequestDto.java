package shop.goodspia.goods.dto.order;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class OrderRequestDto {

    @NotNull
    private int quantity;
    @NotNull
    private int totalPrice;
    @NotNull
    private Long goodsId;
}
