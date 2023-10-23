package shop.goodspia.goods.security.filter;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.goodspia.goods.security.service.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

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

        //Access 토큰 Claims 추출
        Claims accessTokenClaims = jwtUtil.getClaims(accessToken);

        //Access 토큰 유효성 검사 - 서명 확인, 만료 여부 확인
        boolean isValid = validateAccessToken(accessToken);
        if (!isValid) {
            //Access 토큰이 유효하지 않을 경우, 401 에러
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            //Access 토큰이 유효할 경우, 정상 진행
            setAuthenticationToContext(accessToken);
            //Claim으로 저장된 회원 이메일과 아티스트 번호를 요청에 추가
            setClaimsToRequest(request, accessTokenClaims);
            filterChain.doFilter(request, response);
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

    private boolean validateAccessToken(String accessToken) {
        //Access 토큰 유효성 + 만료 여부 체크
        return jwtUtil.validateToken(accessToken) &&
                !jwtUtil.getClaims(accessToken).getExpiration().before(Timestamp.from(Instant.now()));
    }
}
