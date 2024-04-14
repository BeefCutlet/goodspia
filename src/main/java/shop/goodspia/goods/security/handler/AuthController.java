package shop.goodspia.goods.security.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.security.dto.AccessToken;
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
public class AuthController {

    private final LoginService loginService;

    /**
     * 로그인 - 아이디(이메일), 패스워드 입력
     */
    @PostMapping("/login")
    public ResponseEntity<AccessToken> login(@RequestBody @Valid final AuthRequest request) {
        //로그인 인증 - 성공 시 액세스 토큰 + 리프레시 토큰 생성
        AuthResponse authResponse = loginService.login(request);
        //리프레시 토큰 쿠키 생성 - HttpOnly, Secure 설정
        ResponseCookie refreshTokenCookie = createRefreshTokenCookie(authResponse.getRefreshToken());
        log.info("AccessToken: {}", authResponse.getAccessToken().getAccessToken());
        log.info("RefreshToken: {}", authResponse.getRefreshToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(authResponse.getAccessToken());
    }

    /**
     * 액세스 토큰 재발급
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<AccessToken> getRefreshToken(HttpServletRequest request) {
        //리프레스 토큰 추출
        String refreshToken = resolveRefreshToken(request);

        //액세스 토큰 + 리프레시 토큰 재발급
        AuthResponse authResponse = loginService.getNewTokens(refreshToken);
        //리프레시 토큰 쿠키 생성 - HttpOnly, Secure 설정
        ResponseCookie refreshTokenCookie = createRefreshTokenCookie(authResponse.getRefreshToken());
        log.info("액세스 토큰 재발급 성공- 액세스 토큰: {}, 리프레시 토큰: {}", authResponse.getAccessToken().getAccessToken(), authResponse.getRefreshToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(authResponse.getAccessToken());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        //리프레시 토큰 추출
        String refreshToken = resolveRefreshToken((request));
        //리프레시 토큰 정보 삭제
        loginService.deleteRefreshToken(refreshToken);
        return ResponseEntity.ok().build();
    }

    //Refresh 토큰 추출 메서드
    private String resolveRefreshToken(HttpServletRequest request) {
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new IllegalArgumentException("리프레시 토큰을 담은 쿠키가 존재하지 않습니다.");
        }

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

    //Refresh 토큰 쿠키 생성 메서드 - HttpOnly, Secure 설정
    private ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from(TokenInfo.REFRESH_TOKEN, refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite(SameSiteCookies.STRICT.getValue())
                .domain("goodspia.shop")
                .maxAge(TokenInfo.REFRESH_EXP)
                .build();
    }
}
