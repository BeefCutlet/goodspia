package shop.goodspia.goods.dto;

import lombok.Getter;
import lombok.Setter;
import shop.goodspia.goods.entity.Design;
import shop.goodspia.goods.entity.Goods;
import shop.goodspia.goods.entity.Member;

@Getter @Setter
public class CartDto {

    private int quantity;
    private long memberId;
    private long goodsId;
    private long designId;
}
