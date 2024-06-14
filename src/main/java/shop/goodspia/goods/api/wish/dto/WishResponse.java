package shop.goodspia.goods.api.wish.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WishResponse {

    private Long goodsId;
    private String thumbnail;
    private String goodsName;
    private int price;
    private String artistNickname;
    private boolean wishStatus;

    public void modifyWishStatus(boolean wishStatus) {
        this.wishStatus = wishStatus;
    }
}
