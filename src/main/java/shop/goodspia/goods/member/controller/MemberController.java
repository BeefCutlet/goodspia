package shop.goodspia.goods.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.member.dto.MemberSaveRequest;
import shop.goodspia.goods.member.dto.MemberUpdateRequest;
import shop.goodspia.goods.member.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Value("${base-url}")
    private String baseUrl;

    /**
     * 회원 등록 API
     * @param memberInfo
     * @return
     */
    @PostMapping
    public ResponseEntity<?> registerMemberInfo(@RequestBody @Valid MemberSaveRequest memberInfo) {
        memberService.saveMember(memberInfo);
        return ResponseEntity.created(URI.create(baseUrl + "/members/info")).build();
    }

    /**
     * 회원 정보 수정 API
     * @param memberInfo
     * @return
     */
    @PutMapping
    public ResponseEntity<?> modifyMemberInfo(@RequestBody @Valid MemberUpdateRequest memberInfo,
                                              HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");
        memberService.modifyMemberInfo(memberId, memberInfo);
        return ResponseEntity.created(URI.create(baseUrl + "/members/info")).build();
    }
}
