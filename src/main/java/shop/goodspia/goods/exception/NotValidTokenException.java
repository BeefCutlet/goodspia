package shop.goodspia.goods.exception;

public class NotValidTokenException extends RuntimeException {

    public NotValidTokenException() {
    }

    public NotValidTokenException(String message) {
        super(message);
    }
}
