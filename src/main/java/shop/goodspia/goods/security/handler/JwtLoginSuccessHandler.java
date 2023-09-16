package shop.goodspia.goods.security.handler;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import shop.goodspia.goods.member.entity.Member;
import shop.goodspia.goods.member.repository.MemberRepository;
import shop.goodspia.goods.security.dto.AuthResponse;
import shop.goodspia.goods.security.dto.TokenInfo;
import shop.goodspia.goods.security.service.JwtUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class JwtLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    private Gson gson = new Gson();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Member member = memberRepository.findByEmail((String) authentication.getPrincipal())
                .orElseThrow(() -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다."));

        ////RefreshToken 생성 - Cookie로 전달
        String refreshToken = createRefreshToken(member);
        //쿠키 생성
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .build();

        ////AccessToken 생성 - Body로 전달
        String accessToken = createAccessToken(member);

        //바디 설정 - AccessToken
        String successResponse = gson.toJson(new AuthResponse(accessToken));

        //헤더 설정 - RefreshToken(SET_COOKIE), AccessToken(AUTHORIZATION)
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(HttpHeaders.AUTHORIZATION, accessToken);
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(successResponse);
    }

    //RefreshToken 생성 메서드
    private String createRefreshToken(Member member) {
        return jwtUtil.createToken(jwtUtil.createClaims(
                TokenInfo.REFRESH_TOKEN,
                member.getEmail(),
                member.getId(),
                member.getArtist().getId()),
                TokenInfo.REFRESH_EXP);
    }

    //AccessToken 생성 메서드
    private String createAccessToken(Member member) {
        return jwtUtil.createToken(jwtUtil.createClaims(
                TokenInfo.ACCESS_TOKEN,
                member.getEmail(),
                member.getId(),
                member.getArtist().getId()),
                TokenInfo.ACCESS_EXP);
    }
}
