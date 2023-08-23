package shop.goodspia.goods.dto.iamport;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class IAmPortRequestDto {
    @NotNull
    private String merchant_uid;
    @NotNull
    private int amount;
}
