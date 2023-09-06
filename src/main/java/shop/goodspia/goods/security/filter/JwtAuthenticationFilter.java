package shop.goodspia.goods.security.filter;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.goodspia.goods.dto.auth.AuthResponse;
import shop.goodspia.goods.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        //Access 토큰 추출 및 Null 체크, Bearer 인증타입 체크
        String accessToken = resolveAccessToken(request);

        if (accessToken == null) {
            //Access 토큰이 없을 경우, 401 에러
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

        //Access 토큰 유효성 검사 - 서명 확인, 만료 여부 확인
        boolean isAccessValid = jwtUtil.validateToken(accessToken);
        if (!isAccessValid) {
            //Access 토큰이 유효하지 않을 경우, 401 에러
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            //Access 토큰 만료 여부 체크
            if (!jwtUtil.getClaims(accessToken).getExpiration().before(new Date())) {
                //Access 토큰이 만료되지 않았을 경우, 정상 진행
                filterChain.doFilter(request, response);
                return;
            }
        }

        ////Access 토큰이 만료되었을 경우, Access 토큰 재발행을 위해 Refresh 토큰 검사
        //Refresh 토큰 추출
        String refreshToken = resolveRefreshToken(request);

        //Refresh 토큰 유효성 검사 - 서명 확인, 만료 여부 확인
        boolean isRefreshValid = jwtUtil.validateToken(refreshToken);
        if (!isRefreshValid) {
            //Refresh 토큰이 유효하지 않을 경우, 401 에러
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            //Refresh 토큰의 Claim 추출
            Claims claims = jwtUtil.getClaims(refreshToken);
            //Refresh 토큰 만료 여부 체크
            if (claims.getExpiration().before(new Date())) {
                //Refresh 토큰이 만료되었을 경우, 401 에러
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }

            //Refresh 토큰 검증이 끝났다면, Access 토큰 재발행
            //Claim 생성
            Long memberId = claims.get("memberId", Long.class);
            Long artistId = claims.get("artistId", Long.class);
            //Access 토큰 생성
            String remakeAccessToken = jwtUtil.createAccessToken(jwtUtil.createClaims(memberId, artistId));
            Gson gson = new Gson();
            //Access 토큰 재발행
            response.getWriter().write(gson.toJson(new AuthResponse(remakeAccessToken)));
        }
    }

    //Access 토큰 추출 메서드
    private String resolveAccessToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7);
    }

    //Refresh 토큰 추출 메서드
    private String resolveRefreshToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.SET_COOKIE);
        if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7).split(";")[0];
    }
}
