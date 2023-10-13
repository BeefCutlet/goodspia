package shop.goodspia.goods.security.handler;

import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.common.dto.Response;
import shop.goodspia.goods.security.dto.AuthResponse;
import shop.goodspia.goods.security.service.JwtUtil;
import shop.goodspia.goods.security.service.RefreshTokenService;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Refresh 토큰 재발급 API", description = "Access 토큰이 만료되었을 때, Refresh 토큰과 이용해 재발급을 요청할 때 사용하는 API")
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class JwtRefreshTokenRemakeHandler {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    /**
     * Access 토큰이 만료되었을 때, Refresh 토큰과 이용해 재발급을 요청할 때 사용하는 API
     * @param token
     * @return
     */
    @Operation(summary = "Access 토큰 재발급을 위한 API", description = "유효한 Refresh 토큰 필요")
    @PostMapping("/token")
    public ResponseEntity<AuthResponse> createNewAccessToken(@Parameter(hidden = true)
                                                             @RequestHeader(HttpHeaders.SET_COOKIE) String token) {
        log.info("RefreshToken in Cookie={}", token);
        ////Access 토큰이 만료되었을 경우, Access 토큰 재발급을 위해 Refresh 토큰 검사
        //Refresh 토큰 추출
        String refreshToken = resolveRefreshToken(token);
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
    private String resolveRefreshToken(String token) {
        if (!StringUtils.hasText(token)) {
            log.info("Refresh 토큰이 존재하지 않습니다.");
            throw new IllegalArgumentException("Refresh 토큰이 존재하지 않습니다.");
        }
        return token.split("=")[1].split(";")[0];
    }

    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class})
    public ResponseEntity<Response<?>> tokenNotValid(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Response.of(e.getMessage(), null));
    }
}
