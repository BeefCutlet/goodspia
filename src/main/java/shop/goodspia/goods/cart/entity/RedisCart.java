package shop.goodspia.goods.cart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RedisCart {

    private int quantity;
    private Long goodsId;
    private String goodsName;
    private String goodsSummary;
    private String goodsCategory;
    private String goodsThumbnail;
    private int goodsPrice;
    private Long designId;
    private String designName;

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }
}
