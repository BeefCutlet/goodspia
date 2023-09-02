package shop.goodspia.goods.dto.payment;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class PaymentRequest {

    @NotBlank
    private String paymentUid;
    @NotBlank
    private String orderUid;
    @NotNull
    private int amount;
    @NotBlank
    private String cardBank;
    @NotNull
    @CreditCardNumber
    private String cardNumber;
}
