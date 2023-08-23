package shop.goodspia.goods.dto.payment;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PaymentListRequestDto {

    private List<PaymentRequestDto> paymentList;
}
