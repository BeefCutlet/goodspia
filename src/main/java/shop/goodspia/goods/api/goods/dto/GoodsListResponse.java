package shop.goodspia.goods.api.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GoodsListResponse {

    private List<GoodsResponse> goodsList;
    private int totalPage;
}
