package shop.goodspia.goods.security.handler;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import shop.goodspia.goods.common.exception.dto.ErrorCode;
import shop.goodspia.goods.common.exception.dto.ErrorResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Gson gson = new Gson();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        log.error("Authorization Error, exception={}", authException.getMessage());
        //401 에러 반환
        sendError(response, authException.getMessage());
    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        String errorResponse = gson.toJson(ErrorResponse.from(ErrorCode.NOT_PERMITTED, message));
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(errorResponse);
    }
}
