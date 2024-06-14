package shop.goodspia.goods.api.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class PaymentPrepareResponse {

    @NotNull
    @Pattern(regexp = "(ORDER_)([0-9a-zA-Z])+")
    private String merchant_uid;

    @Min(1000)
    @Max(100000000)
    private int amount;
}