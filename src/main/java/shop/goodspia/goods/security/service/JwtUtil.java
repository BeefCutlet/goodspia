package shop.goodspia.goods.security.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shop.goodspia.goods.security.dto.TokenInfo;

import java.security.Key;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

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
    public boolean validateToken(String token) throws
            ExpiredJwtException,
            UnsupportedJwtException,
            MalformedJwtException,
            SignatureException,
            IllegalArgumentException {
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        return true;
    }
}
