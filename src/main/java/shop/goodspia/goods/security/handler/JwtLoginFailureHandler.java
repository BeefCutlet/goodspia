package shop.goodspia.goods.security.handler;

import com.google.gson.Gson;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import shop.goodspia.goods.common.dto.Response;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtLoginFailureHandler implements AuthenticationFailureHandler {

    private Gson gson = new Gson();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //401 에러 반환
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String errorResponse = gson.toJson(Response.of(exception.getMessage(), null));
        response.getWriter().write(errorResponse);
    }
}
