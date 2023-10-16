package shop.goodspia.goods.cart.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import shop.goodspia.goods.cart.entity.RedisCart;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CartRedisRepository {

    private final RedisTemplate<String, RedisCart> redisTemplate;

    /**
     * 장바구니 데이터 단건 저장
     * @param redisCart
     */
    public void save(RedisCart redisCart) {
        log.info("key generate start={}", System.currentTimeMillis());
        String key = KeyGen.generateCartKey(redisCart.getMemberId());
        log.info("key generate end={}", System.currentTimeMillis());
        redisTemplate.opsForList().rightPush(key, redisCart);
        redisTemplate.expire(key, 120, TimeUnit.SECONDS);
    }

    /**
     * 장바구니 데이터 List 조회
     * @param memberId
     * @return
     */
    public List<RedisCart> findListByMemberId(Long memberId) {
        String key = KeyGen.generateCartKey(memberId);
        ListOperations<String, RedisCart> operations = redisTemplate.opsForList();
        Long size = operations.size(key);
        return (size != null) ? operations.range(key, 0, size) : null;
    }

    static class KeyGen {
        private static final String CART_KEY = "cart";
        public static String generateCartKey(Long memberId) {
            return CART_KEY + ":" + memberId;
        }
    }
}
