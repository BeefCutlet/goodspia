package shop.goodspia.goods.payment.dto;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.*;

@Getter
@Builder
public class PaymentRequest {

    @NotNull
    private String paymentUid;

    @NotBlank
    @Pattern(regexp = "(ORDER_)([0-9a-zA-Z])+")
    private String orderUid;

    @Min(1000)
    @Max(100000000)
    private int amount;

    @NotNull
    private String cardBank;

    @NotNull
    @CreditCardNumber
    private String cardNumber;
}
