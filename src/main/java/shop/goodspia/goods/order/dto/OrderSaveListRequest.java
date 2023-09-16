package shop.goodspia.goods.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSaveListRequest {

    private List<OrderSaveRequest> orders;
}
