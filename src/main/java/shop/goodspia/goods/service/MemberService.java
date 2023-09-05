package shop.goodspia.goods.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.dto.member.MemberRequestDto;
import shop.goodspia.goods.entity.Member;
import shop.goodspia.goods.repository.MemberRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 정보 저장
     * @param memberRequestDto
     * @return
     */
    public Long saveMember(MemberRequestDto memberRequestDto) {
        Member member = Member.createMember(memberRequestDto);
        return memberRepository.save(member).getId();
    }

    /**
     * 회원 정보 수정
     * @param memberRequestDto
     */
    public void modifyMemberInfo(MemberRequestDto memberRequestDto) {
        Member member = memberRepository.findById(memberRequestDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));
        member.updateMember(memberRequestDto);
    }

    /**
     * 단일 회원 정보 조회용 메서드
     * @param email
     * @return
     */
    public Member getMemberInfo(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));
    }
}
