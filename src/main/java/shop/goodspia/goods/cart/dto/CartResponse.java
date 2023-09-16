package shop.goodspia.goods.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponse {

    private int quantity;
    private String goodsName;
    private String designName;
    private int price;
    private String mainImage;
}
