package shop.goodspia.goods.common.exception;

public class PasswordNotMatchedException extends RuntimeException {

    public PasswordNotMatchedException() {
    }

    public PasswordNotMatchedException(String message) {
        super(message);
    }
}
