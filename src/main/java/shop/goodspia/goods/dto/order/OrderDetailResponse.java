package shop.goodspia.goods.dto.order;

import lombok.Getter;
import shop.goodspia.goods.entity.Address;
import shop.goodspia.goods.entity.DeliveryStatus;
import shop.goodspia.goods.entity.OrderGoods;

import java.time.LocalDateTime;

@Getter
public class OrderDetailResponse {

    private String orderUid;
    private LocalDateTime createdTime;
    private Long goodsId;
    private String goodsName;
    private String goodsSummary;
    private String goodsImage;

    public OrderDetailResponse(OrderGoods orderGoods) {
        this.orderUid = orderGoods.getOrders().getOrderUid();
        this.createdTime = orderGoods.getOrders().getCreatedTime();
        this.goodsId = orderGoods.getGoods().getId();
        this.goodsName = orderGoods.getGoods().getName();
        this.goodsSummary = orderGoods.getGoods().getSummary();
        this.goodsImage = orderGoods.getGoods().getThumbnail();
    }
}
