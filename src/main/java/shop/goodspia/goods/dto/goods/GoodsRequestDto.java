package shop.goodspia.goods.dto.goods;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GoodsRequestDto {

    private Long id;
    private String name;
    private String summary;
    private String content;
    private String category;
    private String thumbnail;
    private int price;
    private List<String> designs;
}
