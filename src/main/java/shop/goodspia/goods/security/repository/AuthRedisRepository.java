package shop.goodspia.goods.security.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import shop.goodspia.goods.common.util.RedisKeyGenerator;

import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AuthRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final RedisKeyGenerator keyGenerator;

    /**
     * Refresh 토큰 저장
     * 기존 토큰을 삭제하고, 새로운 토큰을 저장
     * @param memberId
     * @param refreshToken
     */
    public void save(Long memberId, String refreshToken) {
        try {
            String key = keyGenerator.generateAuthKey(memberId);
            redisTemplate.delete(key);
            redisTemplate.opsForValue().set(key, refreshToken);
            redisTemplate.expire(key, 30, TimeUnit.MINUTES);
            log.info("Refresh 토큰 저장 성공, 회원 ID = {}", memberId);
        } catch (Exception e) {
            log.info("Refresh 토큰 저장 실패, 회원 ID = {}", memberId);
        }
    }

    public String findByKey(Long memberId) {
        String key = keyGenerator.generateAuthKey(memberId);
        return redisTemplate.opsForValue().get(key);
    }
}
