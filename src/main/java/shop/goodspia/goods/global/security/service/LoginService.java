package shop.goodspia.goods.global.security.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import shop.goodspia.goods.global.common.exception.InvalidTokenException;
import shop.goodspia.goods.global.common.exception.PasswordNotMatchedException;
import shop.goodspia.goods.global.common.exception.dto.ErrorCode;
import shop.goodspia.goods.api.member.entity.Member;
import shop.goodspia.goods.api.member.repository.MemberRepository;
import shop.goodspia.goods.global.security.domain.Auth;
import shop.goodspia.goods.global.security.dto.AuthRequest;
import shop.goodspia.goods.global.security.dto.AuthResponse;
import shop.goodspia.goods.global.security.dto.TokenInfo;
import shop.goodspia.goods.global.security.repository.AuthRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * 로그인 후 액세스 토큰 반환
     */
    public AuthResponse login(AuthRequest request) {
        Member member = memberRepository.findByEmailNotFetch(request.getEmail()).orElseThrow(() -> {
            throw new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
        });

        //비밀번호 일치 여부 체크
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new PasswordNotMatchedException("비밀번호가 일치하지 않습니다.");
        }

        //액세스 토큰, 리프레시 토큰 생성 후 반환
        String accessToken = jwtUtil.createAccessToken(member.getId());
        String refreshToken = createRefreshToken(member);
        return AuthResponse.of(accessToken, refreshToken);
    }



    /**
     * 액세스 토큰 검증 후 재발급
     */
    public AuthResponse getNewTokens(String refreshToken) {
        //액세스 토큰에서 회원 번호 추출
        Long memberId = jwtUtil.getClaims(refreshToken).get(TokenInfo.CLAIM_ID, Long.class);

        //DB에서 리프레시 토큰 정보 조회
        Auth auth = authRepository.findAuthByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("저장된 리프레시 토큰이 없습니다."));

        //리프레시 토큰 검증
        if (!validateRefreshToken(auth, refreshToken)) {
            throw new InvalidTokenException(ErrorCode.INVALID_TOKEN);
        }

        //액세스 토큰 + 리프레시 토큰 재발급
        String newAccessToken = jwtUtil.createAccessToken(memberId);
        String newRefreshToken = createRefreshToken(auth.getMember());

        return AuthResponse.of(newAccessToken, newRefreshToken);
    }

    /**
     * 로그아웃 - 리프레시 토큰 정보 삭제 - 공백으로 변경
     */
    public void deleteRefreshToken(String refreshToken) {
        Long memberId = jwtUtil.getClaims(refreshToken).get(TokenInfo.CLAIM_ID, Long.class);

        //DB에서 리프레시 토큰 정보 조회
        Optional<Auth> findAuth = authRepository.findAuthByMemberId(memberId);
        //리프레시 토큰 정보가 존재하면 빈값으로 변경
        findAuth.ifPresent(auth -> auth.updateRefreshToken(""));
    }

    //리프레시 토큰 발급
    private String createRefreshToken(Member member) {
        //리프레스 토큰 생성
        String refreshToken = jwtUtil.createRefreshToken(member.getId());
        Optional<Auth> auth = authRepository.findAuthByMemberId(member.getId());
        //기존에 저장된 리프레시 토큰이 있으면 업데이트, 없으면 저장
        if (auth.isPresent()) {
            auth.get().updateRefreshToken(refreshToken);
        } else {
            authRepository.save(Auth.of(refreshToken, member));
        }

        //리프레시 토큰 재발급
        return refreshToken;
    }

    //리프레시 토큰 검증
    private boolean validateRefreshToken(Auth auth, String refreshToken) {
        ////토큰 서명 확인
        boolean isRefreshTokenValid = true;

        //Refresh 토큰의 Claim 추출
        Claims refreshTokenClaims = jwtUtil.getClaims(refreshToken);

        ////토큰 서명 확인 + DB에 저장된 Refresh 토큰과 일치하는지 검사 + 만료 여부 검사
        //Refresh 토큰 만료 여부 체크
        if (!StringUtils.hasText(auth.getRefreshToken()) ||
                !jwtUtil.validateToken(refreshToken) ||
                !auth.getRefreshToken().equals(refreshToken) ||
                refreshTokenClaims.getExpiration().before(Timestamp.from(Instant.now()))) {
            isRefreshTokenValid = false;
        }

        return isRefreshTokenValid;
    }
}
