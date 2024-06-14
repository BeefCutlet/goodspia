package shop.goodspia.goods.global.security.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {

    private AccessToken accessToken;
    private String refreshToken;

    public static AuthResponse of(String accessToken, String refreshToken) {
        return AuthResponse.builder()
                .accessToken(AccessToken.of(accessToken))
                .refreshToken(refreshToken)
                .build();
    }
}
