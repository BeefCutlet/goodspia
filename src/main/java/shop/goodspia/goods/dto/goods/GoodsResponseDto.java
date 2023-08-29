package shop.goodspia.goods.dto.goods;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsResponseDto {

    private Long goodsId;
    private String thumbnail;
    private String goodsName;
    private int price;
}
