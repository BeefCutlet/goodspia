package shop.goodspia.goods.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

//@Configuration
//@EnableRedisRepositories
public class RedisConfig {

//    private String host;
//    private int port;
//
//    public RedisConfig(@Value("${spring.redis.host}") String host,
//                       @Value("${spring.redis.port}") int port) {
//        this.host = host;
//        this.port = port;
//    }
//
//    @Bean
//    public RedisConnectionFactory connectionFactory() {
//        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
//        configuration.setHostName(host);
//        configuration.setPort(port);
//        return new LettuceConnectionFactory(configuration);
//    }
//
//    @Bean
//    public RedisTemplate<String, ?> redisTemplate() {
//        RedisTemplate<String, ?> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory());
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//        return template;
//    }
//
//    @Bean
//    public CacheManager cacheManager() {
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
//                .entryTtl(Duration.ofMinutes(3L));
//
//        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory()).cacheDefaults(redisCacheConfiguration).build();
//    }
}