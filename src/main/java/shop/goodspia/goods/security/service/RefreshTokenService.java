package shop.goodspia.goods.security.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.goodspia.goods.security.dto.AuthResponse;
import shop.goodspia.goods.security.dto.TokenInfo;
import shop.goodspia.goods.security.entity.Auth;
import shop.goodspia.goods.security.repository.AuthRepository;

import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final AuthRepository authRepository;
    private final JwtUtil jwtUtil;

    /**
     * Refresh 토큰 유효성 검사
     * @param memberId
     * @return
     */
    public boolean validateRefreshToken(Long memberId, String refreshToken) {
        Auth auth = authRepository.findAuthByMemberId(memberId)
                .orElseThrow(() -> new IllegalStateException("저장된 리프레시 토큰이 없습니다. 발급이 필요합니다."));

        ////토큰 서명 확인
        boolean isRefreshValid = true;

        //Refresh 토큰의 Claim 추출
        Claims refreshTokenClaims = jwtUtil.getClaims(refreshToken);

        ////토큰 서명 확인 + DB에 저장된 Refresh 토큰과 일치하는지 검사 + 만료 여부 검사
        //Refresh 토큰 만료 여부 체크
        if (jwtUtil.validateToken(refreshToken) ||
                !auth.getRefreshToken().equals(refreshToken) ||
                refreshTokenClaims.getExpiration().before(Timestamp.from(Instant.now()))) {
            isRefreshValid = false;
        }

        return isRefreshValid;
    }

    /**
     * Access 토큰 재발급 메서드
     * @param claims
     * @return
     */
    public AuthResponse recreateAccessToken(Claims claims) {
        String token = jwtUtil.createToken(claims, TokenInfo.ACCESS_EXP);
        return new AuthResponse(token);
    }
}
