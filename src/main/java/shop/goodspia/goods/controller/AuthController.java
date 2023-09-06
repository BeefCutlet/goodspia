package shop.goodspia.goods.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.dto.auth.AuthRequest;
import shop.goodspia.goods.dto.auth.AuthResponse;
import shop.goodspia.goods.entity.Member;
import shop.goodspia.goods.security.dto.TokenName;
import shop.goodspia.goods.service.MemberService;
import shop.goodspia.goods.util.JwtUtil;

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
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest userInfo) {
        //회원 정보 검색 - 아이디 일치 여부 체크
        Member member = memberService.getMemberInfo(userInfo.getUserId());
        if (!passwordEncoder.matches(userInfo.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        ////RefreshToken 생성 - Cookie로 전달
        String refreshToken = jwtUtil.createRefreshToken(jwtUtil.createClaims(member.getId(), member.getArtist().getId()));
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .build();

        ////AccessToken 생성 - Body로 전달
        String accessToken = jwtUtil.createAccessToken(jwtUtil.createClaims(member.getId(), member.getArtist().getId()));

        //바디 설정 - AccessToken
        AuthResponse authResponse = new AuthResponse(accessToken);

        //헤더 설정 - RefreshToken(SET_COOKIE), AccessToken(AUTHORIZATION)
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, accessToken);
        headers.set(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok()
                .headers(headers)
                .body(authResponse);
    }
}
