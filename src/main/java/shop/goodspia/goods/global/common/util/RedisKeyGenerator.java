package shop.goodspia.goods.global.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisKeyGenerator {

    public String generateCartKey(Long memberId) {
        return RedisKey.CART_KEY + ":" + memberId;
    }

    public String generateCartHashKey(Long goodsId, Long designId) {
        return goodsId + ":" + designId;
    }


    public String generateAuthKey(Long memberId) {
        return RedisKey.AUTH_KEY + ":" + memberId;
    }
}
