package shop.goodspia.goods.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.dto.member.MemberRequestDto;
import shop.goodspia.goods.service.MemberService;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 등록 API
     * @param memberRequestDto
     * @return
     */
    @PostMapping("/register")
    public String register(@RequestBody MemberRequestDto memberRequestDto) {
        memberService.saveMember(memberRequestDto);
        return "";
    }

    /**
     * 회원 정보 수정 API
     * @param memberRequestDto
     * @return
     */
    @PatchMapping("/modify/{memberId}")
    public String modifyMember(@PathVariable long memberId,
                               @RequestBody MemberRequestDto memberRequestDto) {

        return "";
    }
}
