package shop.goodspia.goods.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.goodspia.goods.artist.controller.ArtistController;
import shop.goodspia.goods.artist.controller.ArtistQueryController;
import shop.goodspia.goods.common.exception.dto.ErrorCode;
import shop.goodspia.goods.common.exception.dto.ErrorResponse;

@RestControllerAdvice(basePackageClasses = {ArtistQueryController.class, ArtistController.class})
public class ArtistExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalArgumentException(IllegalArgumentException e) {
        return ErrorResponse.from(ErrorCode.DATA_NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalStateException(IllegalStateException e) {
        return ErrorResponse.from(ErrorCode.INVALID_INVOKE, e.getMessage());
    }
}
