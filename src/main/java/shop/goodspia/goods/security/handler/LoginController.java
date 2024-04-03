package shop.goodspia.goods.security.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.security.dto.AuthRequest;
import shop.goodspia.goods.security.dto.AuthResponse;
import shop.goodspia.goods.security.dto.TokenInfo;
import shop.goodspia.goods.security.service.LoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private static final String BEARER_PREFIX = "Bearer ";

    private final LoginService loginService;

    /**
     * 로그인 - 아이디(이메일), 패스워드 입력
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid final AuthRequest request) {
        //로그인 인증 - 성공 시 액세스 토큰 + 리프레시 토큰 생성
        AuthResponse authResponse = loginService.login(request);
        //리프레시 토큰 쿠키 생성 - HttpOnly, Secure 설정
        ResponseCookie refreshTokenCookie = createRefreshTokenCookie(authResponse.getRefreshToken());
        log.info("AccessToken: {}", authResponse.getAccessToken());
        log.info("RefreshToken: {}", authResponse.getRefreshToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(authResponse);
    }

    /**
     * 리프레시 토큰 재발급
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> getRefreshToken(HttpServletRequest request) {
        //리프레스 토큰 추출
        String refreshToken = resolveRefreshToken(request);
        String accessToken = resolveAccessToken(request);

        //액세스 토큰 + 리프레시 토큰 재발급
        AuthResponse authResponse = loginService.getNewTokens(accessToken, refreshToken);
        //리프레시 토큰 쿠키 생성 - HttpOnly, Secure 설정
        ResponseCookie refreshTokenCookie = createRefreshTokenCookie(authResponse.getRefreshToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
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
            throw new IllegalArgumentException("요청 리프레시 토큰이 존재하지 않습니다.");
        }
        return token;
    }

    //Access 토큰 추출 메서드
    private String resolveAccessToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(token) || !token.startsWith(BEARER_PREFIX)) {
            return null;
        }
        return token.substring(BEARER_PREFIX.length());
    }

    //Refresh 토큰 쿠키 생성 메서드 - HttpOnly, Secure 설정
    private ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from(TokenInfo.REFRESH_TOKEN, refreshToken)
                .httpOnly(true)
                .secure(true)
                .build();
    }
}
