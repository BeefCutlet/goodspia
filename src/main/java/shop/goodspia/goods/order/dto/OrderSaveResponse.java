package shop.goodspia.goods.order.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.goodspia.goods.order.entity.Orders;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderSaveResponse {

    private Long orderId;
    private int amount;

    public static OrderSaveResponse from(Orders order) {
        return new OrderSaveResponse(order.getId(), order.getOrderPrice());
    }
}
