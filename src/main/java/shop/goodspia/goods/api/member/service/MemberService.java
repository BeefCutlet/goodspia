package shop.goodspia.goods.api.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.api.member.dto.MemberResponse;
import shop.goodspia.goods.api.member.dto.MemberSaveRequest;
import shop.goodspia.goods.api.member.dto.MemberUpdateRequest;
import shop.goodspia.goods.api.member.entity.Member;
import shop.goodspia.goods.api.member.repository.MemberRepository;
import shop.goodspia.goods.global.security.domain.Auth;
import shop.goodspia.goods.global.security.dto.AuthResponse;
import shop.goodspia.goods.global.security.repository.AuthRepository;
import shop.goodspia.goods.global.security.service.JwtUtil;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthRepository authRepository;
    private final JwtUtil jwtUtil;

    /**
     * 회원 정보 저장
     */
    public AuthResponse saveMember(MemberSaveRequest memberSaveRequest) {
        //이메일로 회원 존재 여부 확인 - 존재하면 예외 발생
        if (memberRepository.findByEmailNotFetch(memberSaveRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        //회원 정보 DB에 저장
        Member member = Member.from(memberSaveRequest, passwordEncoder);
        memberRepository.save(member);

        //액세스 토큰, 리프레시 토큰 발급
        String accessToken = jwtUtil.createAccessToken(member.getId());
        String refreshToken = createRefreshToken(member);

        return AuthResponse.of(accessToken, refreshToken);
    }

    /**
     * 회원 정보 수정
     */
    public void modifyMemberInfo(Long memberId, MemberUpdateRequest memberUpdateRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));
        member.updateMember(memberUpdateRequest);
    }

    /**
     * 단일 회원 정보 조회용 메서드
     */
    public MemberResponse getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));
        return MemberResponse.from(member);
    }

    //리프레시 토큰 발급
    private String createRefreshToken(Member member) {
        //리프레스 토큰 생성
        String refreshToken = jwtUtil.createRefreshToken(member.getId());

        //리프레시 토큰 DB에 저장
        authRepository.save(Auth.of(refreshToken, member));

        //리프레시 토큰 발급
        return refreshToken;
    }
}
