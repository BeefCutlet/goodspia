package shop.goodspia.goods.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.member.dto.MemberResponse;
import shop.goodspia.goods.member.dto.MemberSaveRequest;
import shop.goodspia.goods.member.dto.MemberUpdateRequest;
import shop.goodspia.goods.member.entity.Member;
import shop.goodspia.goods.member.repository.MemberRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 정보 저장
     * @param memberSaveRequest
     * @return
     */
    public Long saveMember(MemberSaveRequest memberSaveRequest) {
        String findMember = memberSaveRequest.getEmail();
        if (memberRepository.findByEmailNotFetch(findMember).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        Member member = Member.builder()
                .email(memberSaveRequest.getEmail())
                .password(passwordEncoder.encode(memberSaveRequest.getPassword()))
                .nickname(memberSaveRequest.getNickname())
                .isWithdraw(0)
                .build();
        return memberRepository.save(member).getId();
    }

    /**
     * 회원 정보 수정
     * @param memberUpdateRequest
     */
    public void modifyMemberInfo(Long memberId, MemberUpdateRequest memberUpdateRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));
        member.updateMember(memberUpdateRequest);
    }

    /**
     * 단일 회원 정보 조회용 메서드
     * @param memberId
     * @return
     */
    public MemberResponse getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));
        return new MemberResponse(member.getEmail(), member.getNickname());
    }
}
