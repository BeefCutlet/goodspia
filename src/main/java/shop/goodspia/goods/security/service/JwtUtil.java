package shop.goodspia.goods.security.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    private final Key key;

    public JwtUtil(@Value("${jwt.secret-key}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    public String createAccessToken(Long memberId) {
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(TokenInfo.ISSUER)
                .setIssuedAt(Timestamp.from(Instant.now()))
                .claim(TokenInfo.CLAIM_ID, memberId)
                .setExpiration(Timestamp.from(Instant.now().plus(TokenInfo.ACCESS_EXP, ChronoUnit.SECONDS)))
                .compact();
    }

    public String createRefreshToken(Long memberId) {
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(TokenInfo.ISSUER)
                .setIssuedAt(Timestamp.from(Instant.now()))
                .claim(TokenInfo.CLAIM_ID, memberId)
                .setExpiration(Timestamp.from(Instant.now().plus(TokenInfo.REFRESH_EXP, ChronoUnit.SECONDS)))
                .compact();
    }

    //Claim 추출 메서드
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
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.info("이미 만료된 JWT 토큰입니다.");
            e.printStackTrace();
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
}
