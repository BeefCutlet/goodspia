package shop.goodspia.goods.dto.payment;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class PaymentRequestDto {

    @NotNull
    private String paymentUid;
    @NotNull
    private String orderUid;
    @NotNull
    private int amount;
    @NotNull
    private String cardBank;
    @NotNull
    private String cardNumber;
}
