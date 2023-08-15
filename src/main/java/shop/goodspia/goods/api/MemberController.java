package shop.goodspia.goods.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.dto.MemberDto;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    /**
     * 회원 등록 API
     * @param memberDto
     * @return
     */
    @PostMapping("/register")
    public String register(@RequestBody MemberDto memberDto) {

        return "";
    }

    /**
     * 회원 정보 수정 API
     * @param memberDto
     * @return
     */
    @PatchMapping("/modify/{memberId}")
    public String modifyMember(@PathVariable long memberId,
                               @RequestBody MemberDto memberDto) {

        return "";
    }
}