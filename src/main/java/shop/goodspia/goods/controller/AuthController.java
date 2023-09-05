package shop.goodspia.goods.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.dto.auth.LoginRequest;
import shop.goodspia.goods.dto.auth.LoginResponse;
import shop.goodspia.goods.entity.Member;
import shop.goodspia.goods.service.MemberService;
import shop.goodspia.goods.util.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "인증 API", description = "로그인 처리 및 토큰 생성을 위한 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "비밀번호 로그인 API", description = "아이디와 비밀번호를 전달하면 인증 후 Access 토큰과 Refresh 토큰을 생성하는 API")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest userInfo) {
        //회원 정보 검색 - 아이디 일치 여부 체크
        Member member = memberService.getMemberInfo(userInfo.getUserId());
        if (!passwordEncoder.matches(userInfo.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        ////RefreshToken 생성 - Cookie로 전달
        String refreshToken = jwtUtil.createRefreshToken("refreshToken");
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .build();

        ////AccessToken 생성 - Body로 전달
        //Claim 생성
        Map<String, Long> accessClaims = new HashMap<>();
        accessClaims.put("memberId", member.getId());
        accessClaims.put("artistId", member.getArtist().getId());
        String accessToken = jwtUtil.createAccessToken("AccessToken", accessClaims);

        //바디 설정 - AccessToken
        LoginResponse loginResponse = new LoginResponse(accessToken);

        //헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, accessToken);
        headers.set(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok()
                .headers(headers)
                .body(loginResponse);
    }
}
