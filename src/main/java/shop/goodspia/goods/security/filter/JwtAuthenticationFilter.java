package shop.goodspia.goods.security.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.goodspia.goods.security.domain.JwtAuthenticationToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        //Access 토큰 추출 및 Null 체크, Bearer 인증타입 체크
        String accessToken = resolveAccessToken(request);

        if (StringUtils.hasText(accessToken)) {
            try {
                log.info("Authentication Start, accessToken: {}", accessToken);
                final JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(accessToken);
                final Authentication authentication = authenticationManager.authenticate(jwtAuthenticationToken);

                setAuthenticationToContext(authentication);
                log.info("Authentication End");
            } catch (AuthenticationException e) {
                log.info("Authentication Error: {}", e.getMessage());
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }

    //Access 토큰 추출 메서드
    private String resolveAccessToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(token) || !token.startsWith(BEARER_PREFIX)) {
            log.info("유효한 Access 토큰이 존재하지 않습니다.");
            return null;
        }
        return token.substring(BEARER_PREFIX.length());
    }

    //Authentication 객체를 SecurityContextHolder에 저장
    private void setAuthenticationToContext(Authentication authentication) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }
}
