package shop.goodspia.goods.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.member.dto.MemberResponse;
import shop.goodspia.goods.member.service.MemberService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberQueryController {

    private final MemberService memberService;

    @GetMapping("/info")
    public ResponseEntity<MemberResponse> getMemberInfo(HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");
        MemberResponse memberInfo = memberService.getMemberInfo(memberId);

        return ResponseEntity.ok(memberInfo);
    }
}
