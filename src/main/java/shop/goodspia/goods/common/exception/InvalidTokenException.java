package shop.goodspia.goods.common.exception;

import shop.goodspia.goods.common.exception.dto.ErrorCode;

public class InvalidTokenException extends RuntimeException {

    private final ErrorCode errorCode;

    public InvalidTokenException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
