package shop.goodspia.goods.common.exception;

public class DesignNotFoundException extends RuntimeException {

    public DesignNotFoundException() {
    }

    public DesignNotFoundException(String message) {
        super(message);
    }

    public DesignNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DesignNotFoundException(Throwable cause) {
        super(cause);
    }

    public DesignNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
