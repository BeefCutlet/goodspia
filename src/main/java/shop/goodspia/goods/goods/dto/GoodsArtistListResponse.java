package shop.goodspia.goods.goods.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoodsArtistListResponse {
    private List<GoodsArtistResponse> goodsList;
}
