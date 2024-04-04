package shop.goodspia.goods.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GoodsListResponse {

    private List<GoodsResponse> goodsList;
    private int totalPage;
}
