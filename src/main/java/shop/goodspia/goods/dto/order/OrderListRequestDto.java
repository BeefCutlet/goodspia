package shop.goodspia.goods.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderListRequestDto {

    @NotNull
    private List<OrderRequestDto> orders;
}
