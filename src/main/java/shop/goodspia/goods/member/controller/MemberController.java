package shop.goodspia.goods.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "회원 등록/수정/삭제 API", description = "회원가입 및 회원정보 수정, 회원 탈퇴 처리를 하는 API")
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
    @Operation(summary = "회원 등록 API", description = "새로운 회원을 등록하는 API")
    @PostMapping
    public ResponseEntity<?> register(@Parameter(name = "회원 정보", description = "회원가입 할 회원의 정보")
                                      @Valid @RequestBody MemberSaveRequest memberInfo) {
        memberService.saveMember(memberInfo);
        return ResponseEntity.created(URI.create(baseUrl + "/members/info")).build();
    }

    /**
     * 회원 정보 수정 API
     * @param memberInfo
     * @return
     */
    @Operation(summary = "회원 정보 수정 API", description = "회원의 비밀번호, 닉네임을 수정할 수 있습니다.")
    @PutMapping
    public ResponseEntity<?> modifyMember(@Parameter(name = "회원 정보", description = "수정될 회원의 정보")
                                          @RequestBody @Valid MemberUpdateRequest memberInfo,
                                          @Parameter(hidden = true) HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");
        memberService.modifyMemberInfo(memberId, memberInfo);
        return ResponseEntity.created(URI.create(baseUrl + "/members/info")).build();
    }
}
