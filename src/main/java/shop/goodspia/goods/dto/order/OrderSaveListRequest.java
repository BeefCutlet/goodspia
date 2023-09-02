package shop.goodspia.goods.dto.order;

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
