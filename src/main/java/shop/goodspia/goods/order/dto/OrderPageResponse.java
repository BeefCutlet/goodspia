package shop.goodspia.goods.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.Valid;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderPageResponse<T> {

    @Valid
    private List<T> orderList;
    private int totalPage;
}
