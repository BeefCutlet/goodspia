package shop.goodspia.goods.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponseDto {

    private int quantity;
    private String goodsName;
    private String design;
    private int price;
    private String mainImage;
}
