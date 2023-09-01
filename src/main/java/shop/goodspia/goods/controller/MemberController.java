package shop.goodspia.goods.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.dto.member.MemberRequestDto;
import shop.goodspia.goods.service.MemberService;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 등록 API
     * @param memberRequestDto
     * @return
     */
    @PostMapping
    public String register(@RequestBody MemberRequestDto memberRequestDto) {
        memberService.saveMember(memberRequestDto);
        return "";
    }

    /**
     * 회원 정보 수정 API
     * @param memberRequestDto
     * @return
     */
    @PatchMapping
    public String modifyMember(@RequestBody MemberRequestDto memberRequestDto) {

        return "";
    }
}
