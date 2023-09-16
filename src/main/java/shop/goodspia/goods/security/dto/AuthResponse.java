package shop.goodspia.goods.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(name = "인증 후 응답 DTO", description = "인증 후 Access 토큰을 반환")
@Getter
@AllArgsConstructor
public class AuthResponse {

    @Schema(description = "Access 토큰")
    private String accessToken;
}
