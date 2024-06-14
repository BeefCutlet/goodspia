package shop.goodspia.goods.global.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.goodspia.goods.global.common.exception.InvalidTokenException;
import shop.goodspia.goods.global.common.exception.dto.ErrorCode;
import shop.goodspia.goods.global.common.exception.dto.ErrorResponse;
import shop.goodspia.goods.global.security.handler.AuthController;

@RestControllerAdvice(basePackageClasses = AuthController.class)
public class AuthExceptionHandler {

    /**
     * 401 - 토큰이 유효하지 않음
     */
    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse invalidTokenException(InvalidTokenException e) {
        return ErrorResponse.of(e.getErrorCode());
    }

    /**
     * 401 - 토큰을 찾지 못함
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse illegalArgumentException(IllegalArgumentException e) {
        return ErrorResponse.from(ErrorCode.TOKEN_NOT_FOUND, e.getMessage());
    }
}
