package shop.goodspia.goods.security.filter;

import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.goodspia.goods.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //토큰 추출 및 Null 체크, Bearer 인증타입 체크
        String jwtToken = resolveToken(request);
        if (jwtToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        //Access 토큰 유효성 검사 - 서명 확인, 만료 여부 확인
        if (!jwtUtil.validateToken(jwtToken)) {
            //Access 토큰이 만료되었을 경우
            //Refresh 토큰 체크
            boolean isValid = true;
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    //Refresh 토큰 유효성 검사 - 서명 확인, 만료 여부 확인
                    isValid = jwtUtil.validateToken(cookie.getValue());
                }
            }
            if (!isValid) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }

        ////Refresh 토큰이 유효할 경우
        //Access 토큰 재발급
        Map<String, Long> claims = convertToMap(jwtUtil.getClaims(jwtToken));
        String accessToken = jwtUtil.createAccessToken("AccessToken", claims);
    }

    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7);
    }

    //Claims 객체를 Map 으로 변환
    private Map<String, Long> convertToMap(Claims jwtClaims) {
        Map<String, Long> claims = new HashMap<>();
        claims.put("memberId", jwtClaims.get("memberId", Long.class));
        claims.put("artistId", jwtClaims.get("artistId", Long.class));
        return claims;
    }
}
