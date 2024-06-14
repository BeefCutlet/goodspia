package shop.goodspia.goods.global.security.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RedisAuth {

    private String refreshToken;
    private Long memberId;
}
