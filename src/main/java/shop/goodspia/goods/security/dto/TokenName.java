package shop.goodspia.goods.security.dto;

public enum TokenName {
    ACCESS_TOKEN("AccessToken"),
    REFRESH_TOKEN("RefreshToken");

    public final String tokenName;

    TokenName(String tokenName) {
        this.tokenName = tokenName;
    }
}
