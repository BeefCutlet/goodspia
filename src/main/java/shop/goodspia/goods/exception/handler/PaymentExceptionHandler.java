package shop.goodspia.goods.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.goodspia.goods.api.PaymentController;
import shop.goodspia.goods.dto.Response;
import shop.goodspia.goods.exception.PaymentValidationFailureException;

@Slf4j
@RestControllerAdvice(basePackageClasses = PaymentController.class)
public class PaymentExceptionHandler {

    @ExceptionHandler(PaymentValidationFailureException.class)
    public Response<?> validationFailure(PaymentValidationFailureException e) {
        return Response.of(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
    }

}
