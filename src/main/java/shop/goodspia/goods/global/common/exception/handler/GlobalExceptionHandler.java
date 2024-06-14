package shop.goodspia.goods.global.common.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import shop.goodspia.goods.global.common.exception.InvalidRequestException;
import shop.goodspia.goods.global.common.exception.dto.ErrorCode;
import shop.goodspia.goods.global.common.exception.dto.ErrorResponse;

@Slf4j
@RestControllerAdvice(basePackages = "shop.goodspia.goods")
public class GlobalExceptionHandler {

    //400 - 필드 검증 실패 에러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validationException(MethodArgumentNotValidException e) {
        log.info("유효하지 않은 요청 정보");
        return ErrorResponse.of(ErrorCode.METHOD_ARGS_NOT_VALID);
    }

    //404
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundException(NoHandlerFoundException e) {
        log.info("처리할 수 없는 요청 정보");
        return ErrorResponse.of(ErrorCode.HANDLER_NOT_FOUND);
    }

    //406
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorResponse httpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e) {
        return ErrorResponse.of(ErrorCode.INVALID_MEDIA_TYPE);
    }

    //415
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ErrorResponse httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return ErrorResponse.of(ErrorCode.INVALID_MEDIA_TYPE);
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidRequestException(InvalidRequestException e) {
        return ErrorResponse.of(e.getErrorCode());
    }

}
