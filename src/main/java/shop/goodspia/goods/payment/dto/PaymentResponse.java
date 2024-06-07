package shop.goodspia.goods.payment.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.goodspia.goods.payment.entity.Payments;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentResponse {

    private final int amount;

    public static PaymentResponse from(Payments payments) {
        return new PaymentResponse(payments.getAmount());
    }
}
