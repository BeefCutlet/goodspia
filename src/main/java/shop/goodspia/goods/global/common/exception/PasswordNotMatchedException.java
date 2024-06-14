package shop.goodspia.goods.global.common.exception;

public class PasswordNotMatchedException extends RuntimeException {

    public PasswordNotMatchedException() {
    }

    public PasswordNotMatchedException(String message) {
        super(message);
    }
}
