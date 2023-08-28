package shop.goodspia.goods.dto.order;

import lombok.Builder;
import lombok.Getter;
import shop.goodspia.goods.entity.Address;

@Getter
@Builder
public class OrderReceivedResponseDto {

    private Long goodsId;
    private String goodsName;
    private String goodsDesign;
    private int goodsPrice;
    private String memberNickname;
    private String paymentTime;
    private int quantity;
    private int totalPrice;
    private Address address;
}
