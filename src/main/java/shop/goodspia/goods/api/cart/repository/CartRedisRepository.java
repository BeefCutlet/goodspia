package shop.goodspia.goods.api.cart.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import shop.goodspia.goods.api.cart.entity.RedisCart;
import shop.goodspia.goods.global.common.util.RedisKeyGenerator;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CartRedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisKeyGenerator keyGenerator;

    /**
     * 장바구니 데이터 저장
     */
    public void save(Long memberId, RedisCart redisCart) {
        try {
            String key = keyGenerator.generateCartKey(memberId);
            String hashKey = keyGenerator.generateCartHashKey(redisCart.getGoodsId(), redisCart.getDesignId());
            HashOperations<String, String, RedisCart> opsForHash = redisTemplate.opsForHash();
            opsForHash.put(key, hashKey, redisCart);
            opsForHash.getOperations().expire(key, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.info("장바구니 저장 실패, 회원 ID = {}", memberId);
        }
    }

    /**
     * 장바구니 데이터 List 조회
     * @return
     */
    public List<RedisCart> findAllByMemberId(Long memberId) {
        //키 생성
        String key = keyGenerator.generateCartKey(memberId);
        HashOperations<String, String, RedisCart> opsForHash = redisTemplate.opsForHash();

        //회원이 저장한 장바구니 목록
        return opsForHash.values(key);
    }
}
