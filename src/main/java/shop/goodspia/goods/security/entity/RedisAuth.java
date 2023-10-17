package shop.goodspia.goods.security.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RedisAuth {

    private String refreshToken;
    private Long memberId;
}
