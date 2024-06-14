package shop.goodspia.goods.global.common.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.goodspia.goods.api.cart.controller.CartController;
import shop.goodspia.goods.api.cart.controller.CartQueryController;
import shop.goodspia.goods.global.common.exception.dto.ErrorCode;
import shop.goodspia.goods.global.common.exception.dto.ErrorResponse;

@Slf4j
@RestControllerAdvice(basePackageClasses = {CartQueryController.class, CartController.class})
public class CartExceptionHandler {

    /**
     * 요청 데이터를 찾을 수 없을 때
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse illegalArgumentException(IllegalArgumentException e) {
        return ErrorResponse.from(ErrorCode.DATA_NOT_FOUND, e.getMessage());
    }
}
