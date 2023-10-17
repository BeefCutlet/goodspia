package shop.goodspia.goods.security.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import shop.goodspia.goods.common.util.RedisKeyGenerator;
import shop.goodspia.goods.security.entity.RedisAuth;

import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AuthRedisRepository {

    private final RedisTemplate<String, RedisAuth> redisTemplate;
    private final RedisKeyGenerator keyGenerator;

    /**
     * Refresh 토큰 저장
     * 기존 토큰을 삭제하고, 새로운 토큰을 저장
     * @param memberId
     * @param auth
     */
    public void save(Long memberId, RedisAuth auth) {
        try {
            String key = keyGenerator.generateAuthKey(memberId);
            redisTemplate.delete(key);
            redisTemplate.opsForValue().set(key, auth);
            redisTemplate.expire(key, 10, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.info("Refresh 토큰 저장 실패, 회원 ID = {}", memberId);
        }
    }

    public RedisAuth findByKey(Long memberId) {
        String key = keyGenerator.generateAuthKey(memberId);
        return redisTemplate.opsForValue().get(key);
    }
}
