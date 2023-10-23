package shop.goodspia.goods.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shop.goodspia.goods.cart.entity.RedisCart;

import static shop.goodspia.goods.common.util.RedisKey.AUTH_KEY;
import static shop.goodspia.goods.common.util.RedisKey.CART_KEY;

@Slf4j
@Component
public class RedisKeyGenerator {

    public String generateCartKey(Long memberId) {
        return CART_KEY + ":" + memberId;
    }

    public String generateCartHashKey(Long goodsId, Long designId) {
        return goodsId + ":" + designId;
    }


    public String generateAuthKey(Long memberId) {
        return AUTH_KEY + ":" + memberId;
    }
}
