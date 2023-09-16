package shop.goodspia.goods.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.goodspia.goods.order.controller.OrderController;
import shop.goodspia.goods.order.controller.OrderQueryController;
import shop.goodspia.goods.common.dto.Response;

@Slf4j
@RestControllerAdvice(basePackageClasses = {OrderQueryController.class, OrderController.class})
public class OrderExceptionHandler {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Response<?>> emptyResultEx() {
        return ResponseEntity.ok(Response.of("요청한 데이터가 존재하지 않습니다.", null));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response<?>> dataNotFound(IllegalArgumentException e) {
        return ResponseEntity.ok(Response.of(e.getMessage(), null));
    }
}
