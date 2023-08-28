package shop.goodspia.goods.dto.order;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponseDto {

    private Long ordersGoodsId;
    private int quantity;
    private int totalPrice;
    private Long goodsId;
    private String goodsName;
    private int goodsPrice;
    private String goodsImage;
    private String goodsDesign;
}
