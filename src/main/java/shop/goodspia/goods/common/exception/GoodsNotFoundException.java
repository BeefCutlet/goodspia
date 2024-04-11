package shop.goodspia.goods.common.exception;

public class GoodsNotFoundException extends RuntimeException {

    public GoodsNotFoundException() {
    }

    public GoodsNotFoundException(String message) {
        super(message);
    }

    public GoodsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoodsNotFoundException(Throwable cause) {
        super(cause);
    }

    public GoodsNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
