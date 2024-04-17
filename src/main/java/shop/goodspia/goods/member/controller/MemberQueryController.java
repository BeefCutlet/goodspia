package shop.goodspia.goods.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.member.dto.MemberResponse;
import shop.goodspia.goods.member.service.MemberService;
import shop.goodspia.goods.security.dto.MemberPrincipal;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberQueryController {

    private final MemberService memberService;

    @GetMapping("/info")
    public ResponseEntity<MemberResponse> getMemberInfo(@AuthenticationPrincipal MemberPrincipal principal) {
        Long memberId = principal.getId();
        MemberResponse memberInfo = memberService.getMemberInfo(memberId);

        return ResponseEntity.ok(memberInfo);
    }
}
