package shop.goodspia.goods.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsResponseDto {

    private String goodsName;
    private String image;
    private int price;
    private String artistName;
}
