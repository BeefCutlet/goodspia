package shop.goodspia.goods.api.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponse {

    private Long cartId;

    private int quantity;

    private Long goodsId;

    private String artistNickname;

    private String goodsName;

    private String designName;

    private int price;

    private String thumbnail;
}
