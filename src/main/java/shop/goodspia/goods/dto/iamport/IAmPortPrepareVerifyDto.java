package shop.goodspia.goods.dto.iamport;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IAmPortPrepareVerifyDto {

    private int code;
    private String message;
    private Response response;

    @Getter @Setter
    public static class Response {
        private String merchant_uid;
        private int amount;
    }
}
