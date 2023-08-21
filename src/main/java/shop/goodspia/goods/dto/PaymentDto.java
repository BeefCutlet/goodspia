package shop.goodspia.goods.dto;

import lombok.Getter;
import lombok.Setter;
import shop.goodspia.goods.entity.Delivery;
import shop.goodspia.goods.entity.Goods;
import shop.goodspia.goods.entity.Member;

@Getter @Setter
public class PaymentDto {

    private int quantity;
    private int totalPrice;
    private String accountBank;
    private String accountNumber;
    private Long goodsId;
    private Long memberId;
    private Long deliveryId;

    private Goods goods;
    private Member member;
    private Delivery delivery;
}
