package shop.goodspia.goods.global.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.goodspia.goods.global.common.exception.dto.ErrorCode;
import shop.goodspia.goods.global.common.exception.dto.ErrorResponse;
import shop.goodspia.goods.api.wish.controller.WishController;

@RestControllerAdvice(basePackageClasses = WishController.class)
public class WishExceptionHandler {

    /**
     * 데이터를 찾을 수 없을 때
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalArgumentException(IllegalArgumentException e) {
        return ErrorResponse.from(ErrorCode.DATA_NOT_FOUND, e.getMessage());
    }

    /**
     * 잘못된 호출이 발생했을 때
     */
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalStateException(IllegalStateException e) {
        return ErrorResponse.from(ErrorCode.INVALID_INVOKE, e.getMessage());
    }
}
