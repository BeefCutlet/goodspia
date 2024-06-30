package shop.goodspia.goods.api.order.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.goodspia.goods.api.order.entity.Orders;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderSaveResponse {

    private Long orderId;
    private int amount;
    private String orderUid;

    public static OrderSaveResponse from(Orders order) {
        return new OrderSaveResponse(order.getId(), order.getOrderPrice(), order.getOrderUid());
    }
}
