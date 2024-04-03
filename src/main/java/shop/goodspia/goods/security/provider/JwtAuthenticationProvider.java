package shop.goodspia.goods.security.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import shop.goodspia.goods.exception.dto.ErrorCode;
import shop.goodspia.goods.member.entity.Member;
import shop.goodspia.goods.member.repository.MemberRepository;
import shop.goodspia.goods.security.dto.MemberPrincipal;
import shop.goodspia.goods.security.dto.TokenInfo;
import shop.goodspia.goods.security.domain.JwtAuthenticationToken;
import shop.goodspia.goods.security.service.JwtUtil;

import java.sql.Timestamp;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //액세스 토큰 추출
        String accessToken = ((JwtAuthenticationToken) authentication).getAccessToken();
        //액세스 토큰 검증
        if (validateAccessToken(accessToken)) {
            throw new BadCredentialsException(ErrorCode.BAD_CREDENTIALS.getMessage());
        }

        //액세스 토큰의 Claim에서 회원 번호를 추출하여 회원 정보 조회
        Long memberId = jwtUtil.getClaims(accessToken).get(TokenInfo.CLAIM_ID, Long.class);
        Member member = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new UsernameNotFoundException(ErrorCode.MEMBER_NOT_FOUND.getMessage());
        });
        //principal 객체 생성
        MemberPrincipal memberPrincipal = MemberPrincipal.from(member);

        return new JwtAuthenticationToken(
                memberPrincipal,
                authentication.getCredentials(),
                authentication.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthenticationProvider.class);
    }

    private boolean validateAccessToken(String accessToken) {
        //Access 토큰 유효성 + 만료 여부 체크
        return jwtUtil.validateToken(accessToken) &&
                jwtUtil.getClaims(accessToken).getExpiration().after(Timestamp.from(Instant.now()));
    }
}
