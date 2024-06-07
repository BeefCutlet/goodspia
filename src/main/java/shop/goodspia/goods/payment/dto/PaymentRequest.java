package shop.goodspia.goods.payment.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class PaymentRequest {

    @NotNull
    private String paymentUid;

    @Min(1)
    private Long orderId;

    @Min(1)
    @Max(100000000)
    private int amount;
}
