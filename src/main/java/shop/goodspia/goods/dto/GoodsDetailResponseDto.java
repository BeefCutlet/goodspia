package shop.goodspia.goods.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsDetailResponseDto {

    private Long goodsId;
    private String name;
    private String summary;
    private String mainImage;
    private String content;
    private String category;
    private int price;
    private String artistName;
}
