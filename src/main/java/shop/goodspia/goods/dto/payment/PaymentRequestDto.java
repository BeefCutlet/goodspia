package shop.goodspia.goods.dto.payment;

import lombok.Getter;
import lombok.Setter;
import shop.goodspia.goods.entity.Goods;
import shop.goodspia.goods.entity.Member;

@Getter @Setter
public class PaymentRequestDto {

    private int quantity;
    private int totalPrice;
    private String accountBank;
    private String accountNumber;
    private Long goodsId;
    private Long memberId;

    private Goods goods;
    private Member member;
}
