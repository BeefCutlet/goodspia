package shop.goodspia.goods.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.goodspia.goods.dto.member.MemberRequestDto;
import shop.goodspia.goods.entity.Member;
import shop.goodspia.goods.exception.MemberNotFoundException;
import shop.goodspia.goods.repository.MemberRepository;

@Slf4j
@Service
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
                .orElseThrow(() -> new MemberNotFoundException("Member Data Not Found"));
        member.updateMember(memberRequestDto);
    }

    /**
     * 단일 회원 정보 조회용 메서드
     * @param memberId
     * @return
     */
    public Member getMemberInfo(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member Data Not Found"));
    }
}
