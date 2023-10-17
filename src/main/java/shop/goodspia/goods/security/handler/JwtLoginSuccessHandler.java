package shop.goodspia.goods.security.handler;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import shop.goodspia.goods.security.entity.Auth;
import shop.goodspia.goods.security.entity.RedisAuth;
import shop.goodspia.goods.security.repository.AuthRedisRepository;
import shop.goodspia.goods.security.repository.AuthRepository;
import shop.goodspia.goods.security.service.JwtUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class JwtLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final AuthRedisRepository authRedisRepository;

    private Gson gson = new Gson();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("LoginSuccessProcess Start");
        Member member = memberRepository.findByEmail((String) authentication.getPrincipal())
                .orElseThrow(() -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다."));

        ////RefreshToken 생성 - Cookie로 전달
        String refreshToken = createRefreshToken(member);
        //쿠키 생성
        ResponseCookie cookie = ResponseCookie.from(TokenInfo.REFRESH_TOKEN, refreshToken)
                .httpOnly(true)
                .secure(true)
                .build();

//        //Refresh 토큰을 DB에 저장
//        //회원에게 기존 Refresh 토큰 정보가 있는지 확인
//        //기존 Refresh 토큰 정보가 있으면 새로운 Refresh 토큰으로 UPDATE
//        //기존 Refresh 토큰 정보가 없으면 새로운 Refresh 토큰을 저장
//        authRepository.findAuthByMemberId(member.getId())
//                .map(auth -> auth.updateRefreshToken(refreshToken))
//                .orElseGet(() -> {
//                    Auth auth = new Auth(refreshToken, member);
//                    return authRepository.save(auth);
//                });
        authRedisRepository.save(member.getId(), refreshToken);

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
        Long artistId = null;
        if (member.getArtist() != null) {
            artistId = member.getArtist().getId();
        }

        return jwtUtil.createToken(jwtUtil.createClaims(
                TokenInfo.REFRESH_TOKEN,
                member.getEmail(),
                member.getId(),
                artistId),
                TokenInfo.REFRESH_EXP);
    }

    //AccessToken 생성 메서드
    private String createAccessToken(Member member) {
        Long artistId = null;
        if (member.getArtist() != null) {
            artistId = member.getArtist().getId();
        }

        return jwtUtil.createToken(jwtUtil.createClaims(
                TokenInfo.ACCESS_TOKEN,
                member.getEmail(),
                member.getId(),
                artistId),
                TokenInfo.ACCESS_EXP);
    }
}
