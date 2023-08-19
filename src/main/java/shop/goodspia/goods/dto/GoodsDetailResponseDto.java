package shop.goodspia.goods.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoodsDetailResponseDto {

    private String name;
    private String summary;
    private String mainImage;
    private String content;
    private String category;
    private int price;
    private String artistName;
}
