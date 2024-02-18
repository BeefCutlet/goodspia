package shop.goodspia.goods.security.handler;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.common.dto.Response;
import shop.goodspia.goods.security.dto.AuthResponse;
import shop.goodspia.goods.security.dto.TokenInfo;
import shop.goodspia.goods.security.service.JwtUtil;
import shop.goodspia.goods.security.service.RefreshTokenService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class JwtRefreshTokenRemakeHandler {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    /**
     * Access 토큰이 만료되었을 때, Refresh 토큰을 이용해 재발급을 요청할 때 사용하는 API
     * @param request
     * @return
     */
    @PostMapping("/token")
    public ResponseEntity<AuthResponse> createNewAccessToken(HttpServletRequest request) {
        ////Access 토큰이 만료되었을 경우, Access 토큰 재발급을 위해 Refresh 토큰 검사
        //Refresh 토큰 추출
        String refreshToken = resolveRefreshToken(request);
        log.info("RefreshToken in Cookie={}",refreshToken);
        Claims claims = jwtUtil.getClaims(refreshToken);

        //토큰 유효성 검사
        boolean isValid = refreshTokenService.validateRefreshToken(claims.get("memberId", Long.class), refreshToken);
        if (!isValid) {
            throw new IllegalStateException("토큰이 유효하지 않습니다.");
        }

        //Access 토큰 재발급
        AuthResponse authResponse = refreshTokenService.recreateAccessToken(claims);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authResponse);
    }

    //Refresh 토큰 추출 메서드
    private String resolveRefreshToken(HttpServletRequest request) {
        String token = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(TokenInfo.REFRESH_TOKEN)) {
                token = cookie.getValue();
                break;
            }
        }

        if (!StringUtils.hasText(token)) {
            log.info("Refresh 토큰이 존재하지 않습니다.");
            throw new IllegalArgumentException("Refresh 토큰이 존재하지 않습니다.");
        }
        return token;
    }

    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class})
    public ResponseEntity<Response<?>> tokenNotValid(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Response.of(e.getMessage(), null));
    }
}
