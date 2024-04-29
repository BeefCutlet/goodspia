package shop.goodspia.goods.common.util;

public enum ImagePath {
    PROFILE_IMAGE("profile-image"),
    GOODS_CONTENT("goods-content"),
    GOODS_THUMBNAIL("goods-thumbnail"),
    ;

    private final String path;

    ImagePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
