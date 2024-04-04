package shop.goodspia.goods.order.dto;

import lombok.Getter;
import shop.goodspia.goods.order.entity.OrderGoods;

import java.time.LocalDateTime;

@Getter
public class OrderDetailResponse {

    private String orderUid;

    private LocalDateTime createdTime;

    private Long goodsId;

    private String goodsName;

    private String goodsThumbnail;

    public OrderDetailResponse(OrderGoods orderGoods) {
        this.orderUid = orderGoods.getOrders().getOrderUid();
        this.createdTime = orderGoods.getOrders().getCreatedTime();
        this.goodsId = orderGoods.getGoods().getId();
        this.goodsName = orderGoods.getGoods().getName();
        this.goodsThumbnail = orderGoods.getGoods().getThumbnail();
    }
}
