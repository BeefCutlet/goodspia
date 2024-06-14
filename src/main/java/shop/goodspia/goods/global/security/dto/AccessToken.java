package shop.goodspia.goods.global.security.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessToken {

    private String accessToken;

    public static AccessToken of(String accessToken) {
        return new AccessToken(accessToken);
    }
}
