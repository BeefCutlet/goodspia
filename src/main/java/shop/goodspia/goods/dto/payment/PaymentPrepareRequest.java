package shop.goodspia.goods.dto.payment;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
public class PaymentPrepareRequest {

    @NotBlank
    private String merchantUid; //주문 번호
    @NotNull
    private BigDecimal amount; //주문 가격
}
