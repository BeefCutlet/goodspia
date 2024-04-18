package shop.goodspia.goods.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.member.dto.MemberSaveRequest;
import shop.goodspia.goods.member.dto.MemberUpdateRequest;
import shop.goodspia.goods.member.service.MemberService;
import shop.goodspia.goods.security.dto.AccessToken;
import shop.goodspia.goods.security.dto.AuthResponse;
import shop.goodspia.goods.security.dto.MemberPrincipal;
import shop.goodspia.goods.security.dto.TokenInfo;

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
     */
    @PostMapping
    public ResponseEntity<AccessToken> registerMemberInfo(@RequestBody @Valid MemberSaveRequest memberInfo) {
        log.info("MemberInfo: {}", memberInfo.toString());
        //회원 가입
        AuthResponse authResponse = memberService.saveMember(memberInfo);

        //리프레시 토큰 생성
        ResponseCookie refreshTokenCookie = createRefreshTokenCookie(authResponse.getRefreshToken());

        //회원 가입 성공 시 액세스 토큰, 리프레시 토큰(쿠키) 발급
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(authResponse.getAccessToken());
    }

    /**
     * 회원 정보 수정 API
     */
    @PutMapping
    public ResponseEntity<?> modifyMemberInfo(@RequestBody @Valid MemberUpdateRequest memberInfo,
                                              @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        Long memberId = memberPrincipal.getId();
        memberService.modifyMemberInfo(memberId, memberInfo);
        return ResponseEntity.noContent().build();
    }

    //Refresh 토큰 쿠키 생성 메서드 - HttpOnly, Secure 설정
    private ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from(TokenInfo.REFRESH_TOKEN, refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite(SameSiteCookies.STRICT.getValue())
                .domain("goodspia.shop")
                .maxAge(TokenInfo.REFRESH_EXP)
                .build();
    }
}
