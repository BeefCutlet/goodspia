package shop.goodspia.goods.global.common.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.goodspia.goods.api.payment.controller.PaymentController;
import shop.goodspia.goods.global.common.dto.Response;
import shop.goodspia.goods.global.common.exception.PaymentValidationFailureException;

@Slf4j
@RestControllerAdvice(basePackageClasses = PaymentController.class)
public class PaymentExceptionHandler {

    @ExceptionHandler(PaymentValidationFailureException.class)
    public Response<?> validationFailure(PaymentValidationFailureException e) {
        return Response.of(e.getMessage(), null);
    }

}
