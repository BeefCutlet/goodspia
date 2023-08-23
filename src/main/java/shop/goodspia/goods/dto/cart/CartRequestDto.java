package shop.goodspia.goods.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartRequestDto {

    private int quantity;
    private long memberId;
    private long goodsId;
    private long designId;
}
