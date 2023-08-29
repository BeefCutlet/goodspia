package shop.goodspia.goods.dto.order;

import lombok.Getter;
import shop.goodspia.goods.entity.OrderGoods;

@Getter
public class OrderResponseDto {

    private Long ordersGoodsId;
    private int quantity;
    private int totalPrice;
    private Long goodsId;
    private String goodsName;
    private int goodsPrice;
    private String goodsImage;
    private String goodsDesign;

    public OrderResponseDto(OrderGoods orderGoods) {
        this.ordersGoodsId = orderGoods.getId();
        this.quantity = orderGoods.getQuantity();
        this.totalPrice = orderGoods.getTotalPrice();
        this.goodsId = orderGoods.getGoods().getId();
        this.goodsName = orderGoods.getGoods().getName();
        this.goodsPrice = orderGoods.getGoods().getPrice();
        this.goodsImage = orderGoods.getGoods().getThumbnail();
        this.goodsDesign = orderGoods.getGoodsDesign();
    }
}
