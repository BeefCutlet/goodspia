package shop.goodspia.goods.goods.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter @Setter
public class GoodsResponse {

    private Long goodsId;
    private String thumbnail;
    private String goodsName;
    private int price;
}
