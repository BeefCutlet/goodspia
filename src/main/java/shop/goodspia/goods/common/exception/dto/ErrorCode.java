package shop.goodspia.goods.common.exception.dto;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    INVALID_TOKEN(HttpStatus.FORBIDDEN, "유효하지 않은 토큰입니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰을 발견하지 못했습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."),
    BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    INVALID_GENDER(HttpStatus.BAD_REQUEST, "성별 정보가 유효하지 않습니다."),

    METHOD_ARGS_NOT_VALID(HttpStatus.BAD_REQUEST, "요청 정보가 유효하지 않습니다."),
    HANDLER_NOT_FOUND(HttpStatus.BAD_REQUEST, "처리할 수 없는 요청입니다."),
    INVALID_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "잘못된 미디어 타입입니다."),
    ;

    private final HttpStatus status;
    private final String message;

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
