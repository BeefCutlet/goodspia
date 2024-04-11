package shop.goodspia.goods.common.exception;

public class ArtistNotFoundException extends RuntimeException {

    public ArtistNotFoundException() {
    }

    public ArtistNotFoundException(String message) {
        super(message);
    }

    public ArtistNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArtistNotFoundException(Throwable cause) {
        super(cause);
    }

    public ArtistNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
