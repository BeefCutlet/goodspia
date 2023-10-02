package shop.goodspia.goods.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@Getter
public class GoodsListResponse {

    @Valid
    private List<GoodsResponse> goodsList;
    private int totalPage;
}
