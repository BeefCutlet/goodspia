package shop.goodspia.goods.api.wish.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class WishListResponse {

    private List<WishResponse> wishList;

    public static WishListResponse from(List<WishResponse> foundWishList) {
        List<WishResponse> wishList = new ArrayList<>();
        foundWishList.forEach((wish) -> {
            wish.modifyWishStatus(true);
            wishList.add(wish);
        });

        return new WishListResponse(wishList);
    }
}
