package shop.goodspia.goods.cart.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import shop.goodspia.goods.cart.entity.RedisCart;
import shop.goodspia.goods.common.util.RedisKeyGenerator;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CartRedisRepository {

    private final RedisTemplate<String, RedisCart> redisTemplate;
    private final RedisKeyGenerator keyGenerator;

    /**
     * 장바구니 데이터 저장
     * @param redisCart
     */
    public void save(RedisCart redisCart) {
        try {
            String key = keyGenerator.generateCartKey(redisCart.getMemberId());
            redisTemplate.opsForList().rightPush(key, redisCart);
            redisTemplate.expire(key, 120, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.info("장바구니 저장 실패, 회원 ID = {}", redisCart.getMemberId());
        }
    }

    /**
     * 장바구니 데이터 List 조회
     * @param memberId
     * @return
     */
    public List<RedisCart> findListByMemberId(Long memberId) {
        String key = keyGenerator.generateCartKey(memberId);
        ListOperations<String, RedisCart> operations = redisTemplate.opsForList();
        Long size = operations.size(key);
        return (size != null) ? operations.range(key, 0, size) : null;
    }


}
