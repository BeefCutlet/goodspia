package shop.goodspia.goods;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class UtilTest {

    @Test
    void test() {
        Claims claims = Jwts.claims();
        claims.setSubject("greenneuron");
        Date iat = new Date();
        claims.setIssuedAt(iat);
        Date exp = new Date(iat.getTime()+1000*60*60*24);
        claims.setExpiration(exp);
        String random512BitKey = "VGhlIEhUVFAgc3BlY2lmaWNhdGlvbiBvZmZlcnMgc29tZSBzaW1wbGUgbWVhbnMgdG8gYXV0aGVudGljYXRlIHJlcXVlc3Rz";
        log.info("decoded Key={}", Decoders.BASE64URL.decode(random512BitKey));

        SecretKey secretKey = Keys.hmacShaKeyFor(random512BitKey.getBytes());
        log.info("secretKey={}", secretKey.getEncoded());

        JwtParser parser = Jwts.parserBuilder().setSigningKey(secretKey).build();

        String jws = Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
        log.info("jws={}", jws);
        Jws<Claims> claimsJws = parser.parseClaimsJws(jws);

        assertThat(claimsJws.getBody().getSubject()).isEqualTo("greenneuron");
        assertThat(claimsJws.getBody().getExpiration().before(iat)).isFalse();
    }
}
