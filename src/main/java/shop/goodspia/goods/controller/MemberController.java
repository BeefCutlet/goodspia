package shop.goodspia.goods.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.dto.member.MemberRequestDto;
import shop.goodspia.goods.service.MemberService;

@Tag(name = "회원 등록/수정/삭제 API", description = "회원가입 및 회원정보 수정, 회원 탈퇴 처리를 하는 API")
@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 등록 API
     * @param memberInfo
     * @return
     */
    @Operation(summary = "회원 등록 API", description = "새로운 회원을 등록하는 API")
    @PostMapping
    public String register(@Parameter(name = "회원 정보", description = "회원가입 할 회원의 정보") @RequestBody MemberRequestDto memberInfo) {
        memberService.saveMember(memberInfo);
        return "";
    }

    /**
     * 회원 정보 수정 API
     * @param memberInfo
     * @return
     */
    @PatchMapping
    public String modifyMember(@Parameter(name = "회원 정보", description = "수정될 회원의 정보") @RequestBody MemberRequestDto memberInfo) {

        return "";
    }
}
