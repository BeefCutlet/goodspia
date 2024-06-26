package shop.goodspia.goods.api.goods.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GoodsResponse {

    private Long goodsId;
    private String thumbnail;
    private String goodsName;
    private int price;
    private int wishCount;
    private String artistNickname;
}
