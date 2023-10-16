package shop.goodspia.goods.cart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RedisCart {

    private int quantity;
    private Long memberId;
    private Long goodsId;
    private Long designId;
}
