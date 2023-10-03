package shop.goodspia.goods.security.handler;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import shop.goodspia.goods.common.dto.Response;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private Gson gson = new Gson();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        log.info("Authorization Error, exception={}", authException.getMessage());
        if (authException instanceof InsufficientAuthenticationException) {
            String errorResponse = gson.toJson(Response.of("인증에 실패하였습니다.", null));
            response.getWriter().write(errorResponse);
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
