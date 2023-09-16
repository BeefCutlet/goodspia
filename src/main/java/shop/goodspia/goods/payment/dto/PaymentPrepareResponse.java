package shop.goodspia.goods.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentPrepareResponse {

    private String merchant_uid;
    private int amount;
}