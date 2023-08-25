package shop.goodspia.goods.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentPrepareResponseDto {

    private String merchant_uid;
    private int amount;
}