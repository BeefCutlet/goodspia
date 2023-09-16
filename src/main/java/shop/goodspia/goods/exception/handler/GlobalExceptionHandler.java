package shop.goodspia.goods.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import shop.goodspia.goods.common.dto.Response;

@RestControllerAdvice(basePackages = "shop.goodspia.goods.controller")
public class GlobalExceptionHandler {

    //400 - 필드 검증 실패 에러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response validationException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return Response.of(e.getMessage(), null);
    }

    //404
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response notFoundException(NoHandlerFoundException e) {
        return Response.of(e.getMessage(), null);
    }

    //406
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Response httpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e) {
        return Response.of(e.getMessage(), null);
    }

    //415
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public Response httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return Response.of(e.getMessage(), null);
    }

}
