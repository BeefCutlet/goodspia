package shop.goodspia.goods.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GoodsDto {

    private Long id;
    private String name;
    private String summary;
    private String content;
    private String category;
    private String image;
}
