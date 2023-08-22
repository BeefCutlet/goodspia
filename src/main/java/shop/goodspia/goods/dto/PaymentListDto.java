package shop.goodspia.goods.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PaymentListDto {

    private List<PaymentDto> paymentDtos;
}
