package shop.goodspia.goods.order.dto;

import lombok.Getter;
import shop.goodspia.goods.order.entity.OrderGoods;

import javax.validation.constraints.*;

@Getter
public class OrderResponse {

    @Min(1)
    private Long ordersGoodsId;

    @Min(1)
    @Max(1000)
    private int quantity;

    @Min(1000)
    @Max(100000000)
    private int totalPrice;

    @Min(1)
    private Long goodsId;

    @NotNull
    @Pattern(regexp = "[0-9a-zA-Z가-힣]")
    @Size(min = 1, max = 50)
    private String goodsName;

    @Min(1000)
    @Max(10000000)
    private int goodsPrice;

    private String goodsImage;

    @NotNull
    @Pattern(regexp = "[0-9a-zA-Z가-힣]")
    private String goodsDesign;

    public OrderResponse(OrderGoods orderGoods) {
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
