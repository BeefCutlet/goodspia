package shop.goodspia.goods.global.security.dto;

import java.time.Duration;

public class TokenInfo {

    public static final String ACCESS_TOKEN = "AccessToken";
    public static final String REFRESH_TOKEN = "RefreshToken";
    public static final long ACCESS_EXP = Duration.ofMinutes(5).toSeconds();
    public static final long REFRESH_EXP = Duration.ofMinutes(30).toSeconds();
    public static final String ISSUER = "GoodsPia";
    public static final String CLAIM_ID = "id";
}
