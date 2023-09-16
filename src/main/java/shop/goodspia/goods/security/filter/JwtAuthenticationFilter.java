package shop.goodspia.goods.security.filter;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.goodspia.goods.security.dto.AuthResponse;
import shop.goodspia.goods.security.dto.TokenName;
import shop.goodspia.goods.common.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final long accessTokenExpiration;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, long accessTokenExpiration) {
        this.jwtUtil = jwtUtil;
        this.accessTokenExpiration = accessTokenExpiration;
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
            if (!jwtUtil.getClaims(accessToken).getExpiration().before(Timestamp.from(Instant.now()))) {
                Claims claims = jwtUtil.getClaims(accessToken);
                log.info("exp={}", claims.getExpiration());
                //Access 토큰이 만료되지 않았을 경우, 정상 진행
                setAuthenticationToContext(accessToken);
                //Claim으로 저장된 회원 이메일과 아티스트 번호를 요청에 추가
                setClaimsToRequest(request, claims);
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
            if (claims.getExpiration().before(Timestamp.from(Instant.now()))) {
                //Refresh 토큰이 만료되었을 경우, 401 에러
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }

            //Refresh 토큰 검증이 끝났다면, Access 토큰 재발행
            //Claim 생성
            String email = claims.get("email", String.class);
            Long memberId = claims.get("memberId", Long.class);
            Long artistId = claims.get("artistId", Long.class);
            //Access 토큰 생성
            String remakeAccessToken = jwtUtil.createToken(jwtUtil.createClaims(
                    TokenName.ACCESS_TOKEN.name(),
                    email, memberId,
                    artistId),
                    accessTokenExpiration);
            Gson gson = new Gson();
            //Access 토큰 재발행
            response.getWriter().write(gson.toJson(new AuthResponse(remakeAccessToken)));
        }
    }

    //Access 토큰 추출 메서드
    private String resolveAccessToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
            log.info("유효한 Access 토큰이 존재하지 않습니다.");
            return null;
        }
        return token.substring(7);
    }

    //Refresh 토큰 추출 메서드
    private String resolveRefreshToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.SET_COOKIE);
        if (!StringUtils.hasText(token)) {
            log.info("Refresh 토큰이 존재하지 않습니다.");
            return null;
        }
        return token.split("=")[1].split(";")[0];
    }

    //Authentication 객체 생성 후 SecurityContextHolder에 저장
    private void setAuthenticationToContext(String accessToken) {
        Authentication authentication = jwtUtil.getAuthenticationToken(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Access 토큰 검증 완료");
    }

    //요청에 커스텀 Claim 추가
    private void setClaimsToRequest(HttpServletRequest request, Claims claims) {
        request.setAttribute("email", claims.get("email", String.class));
        request.setAttribute("memberId", claims.get("memberId", Long.class));
        request.setAttribute("artistId", claims.get("artistId", Long.class));
    }
}
