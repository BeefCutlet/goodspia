package shop.goodspia.goods.common.exception.dto;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    NOT_VALID_TOKEN(HttpStatus.FORBIDDEN, "유효하지 않은 토큰입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."),
    BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    ;

    private HttpStatus status;
    private String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
