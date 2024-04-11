package shop.goodspia.goods.common.exception;

public class NotValidTokenException extends RuntimeException {

    public NotValidTokenException() {
    }

    public NotValidTokenException(String message) {
        super(message);
    }
}
