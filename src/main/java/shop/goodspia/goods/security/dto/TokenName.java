package shop.goodspia.goods.security.dto;

public enum TokenName {
    ACCESS_TOKEN("AccessToken"),
    REFRESH_TOKEN("RefreshToken");

    private final String name;

    TokenName(String name) {
        this.name = name;
    }
}
