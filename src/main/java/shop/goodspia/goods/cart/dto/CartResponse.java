package shop.goodspia.goods.cart.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class CartResponse {

    private Long cartId;

    private int quantity;

    private String artistNickname;

    private String goodsName;

    private String designName;

    private int price;

    private String thumbnail;
}
