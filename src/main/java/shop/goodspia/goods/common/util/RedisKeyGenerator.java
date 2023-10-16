package shop.goodspia.goods.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static shop.goodspia.goods.common.util.RedisKey.CART_KEY;

@Slf4j
@Component
public class RedisKeyGenerator {

    public String generateCartKey(Long memberId) {
        log.info("key generate start={}", System.currentTimeMillis());
        String key = CART_KEY + ":" + memberId;
        log.info("key generate end={}", System.currentTimeMillis());
        return key;
    }
}
