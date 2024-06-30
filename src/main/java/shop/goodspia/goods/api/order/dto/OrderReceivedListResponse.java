package shop.goodspia.goods.api.order.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderReceivedListResponse {

    private List<OrderReceivedResponse> orders;

    public static OrderReceivedListResponse from(List<OrderReceivedResponse> receivedOrders) {
        return new OrderReceivedListResponse(receivedOrders);
    }
}
