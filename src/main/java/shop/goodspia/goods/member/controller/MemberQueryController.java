package shop.goodspia.goods.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.member.dto.MemberResponse;
import shop.goodspia.goods.member.service.MemberService;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "회원 조회 API", description = "기존 회원 정보를 조회하는 API")
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberQueryController {

    private final MemberService memberService;

    @Operation(summary = "단일 회원 정보 조회 API", description = "현재 로그인 중인 회원의 정보 조회 API\n이메일, 닉네임 조회")
    @GetMapping("/info")
    public ResponseEntity<MemberResponse> getMemberInfo(@Parameter(hidden = true) HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");
        MemberResponse memberInfo = memberService.getMemberInfo(memberId);

        return ResponseEntity.ok(memberInfo);
    }
}
