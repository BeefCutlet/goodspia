package shop.goodspia.goods.security.dto;

import java.time.Duration;

public class TokenInfo {

    public static final String ACCESS_TOKEN = "AccessToken";
    public static final String REFRESH_TOKEN = "RefreshToken";
    public static final long ACCESS_EXP = Duration.ofMillis(5).toSeconds();
    public static final long REFRESH_EXP = Duration.ofHours(1).toSeconds();
    public static final String ISSUER = "GoodsPia";
}
