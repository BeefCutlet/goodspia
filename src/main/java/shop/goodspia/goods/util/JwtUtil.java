package shop.goodspia.goods.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import shop.goodspia.goods.security.dto.TokenName;

import java.security.Key;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@PropertySource(value = {"classpath:jwt.properties"})
public class JwtUtil implements InitializingBean {

    private final String secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;
    private final String issuer;

    private Key key;

    public JwtUtil(@Value("${secret-key}") String secretKey,
                   @Value("${access.expiration}") long accessTokenExpiration,
                   @Value("${refresh.expiration}") long refreshTokenExpiration,
                   @Value("${issuer}") String issuer) {
        this.secretKey = secretKey;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.issuer = issuer;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String createAccessToken(Map<String, Long> claims) {
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .setSubject(TokenName.ACCESS_TOKEN.name())
                .setClaims(claims)
                .setIssuer(issuer)
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Date.from(Instant.now().plus(accessTokenExpiration, ChronoUnit.HOURS)))
                .compact();
    }

    public String createRefreshToken(Map<String, Long> claims) {
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .setSubject(TokenName.REFRESH_TOKEN.name())
                .setClaims(claims)
                .setIssuer(issuer)
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Date.from(Instant.now().plus(refreshTokenExpiration, ChronoUnit.HOURS)))
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    //토큰 유효성 검사 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token);
            return true;
        } catch (SecurityException | MalformedJwtException exception) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public Map<String, Long> createClaims(Long memberId, Long artistId) {
        Map<String, Long> claims = new HashMap<>();
        claims.put("memberId", memberId);
        claims.put("artistId", artistId);
        return claims;
    }
}
