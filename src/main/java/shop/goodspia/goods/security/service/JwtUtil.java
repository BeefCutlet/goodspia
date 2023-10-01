package shop.goodspia.goods.security.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import shop.goodspia.goods.security.dto.TokenInfo;

import java.security.Key;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Set;

@Slf4j
@Component
public class JwtUtil {

    private Key key;

    public JwtUtil(@Value("${jwt.secret-key}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    public String createToken(Claims claims, long expirationTime) {
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuer(TokenInfo.ISSUER)
                .setIssuedAt(Timestamp.from(Instant.now()))
                .setExpiration(Timestamp.from(Instant.now().plus(expirationTime, ChronoUnit.SECONDS)))
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //토큰 유효성 검사 메서드
    public boolean validateToken(String token) {
        try {
            log.info("try to validate token={}", token);
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            log.info("token validate success, token={}", token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            e.printStackTrace();
        }
        return false;
    }

    public Claims createClaims(String subject, String email, Long memberId, Long artistId) {
        Claims claims = Jwts.claims();
        claims.setSubject(subject);
        claims.put("email", email);
        claims.put("memberId", memberId);
        claims.put("artistId", artistId);
        return claims;
    }

    public Authentication getAuthenticationToken(String accessToken) {
        Claims claims = getClaims(accessToken);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(
                new User((String) claims.get("email"), accessToken, authorities), accessToken, authorities);
    }
}
