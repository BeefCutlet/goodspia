package shop.goodspia.goods.payment.dto;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class PaymentPrepareRequest {

    @NotBlank
    @Pattern(regexp = "(ORDER_)([0-9a-zA-Z])+")
    private String merchantUid; //주문 번호

    @Min(1000)
    @Max(100000000)
    private long amount; //주문 가격
}
