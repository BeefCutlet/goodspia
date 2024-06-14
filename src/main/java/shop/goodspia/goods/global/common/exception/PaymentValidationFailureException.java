package shop.goodspia.goods.global.common.exception;

public class PaymentValidationFailureException extends RuntimeException {

    public PaymentValidationFailureException() {
    }

    public PaymentValidationFailureException(String message) {
        super(message);
    }

    public PaymentValidationFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentValidationFailureException(Throwable cause) {
        super(cause);
    }

    public PaymentValidationFailureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
