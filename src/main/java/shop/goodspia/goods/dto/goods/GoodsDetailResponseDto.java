package shop.goodspia.goods.dto.goods;

import lombok.Getter;
import shop.goodspia.goods.dto.design.DesignResponseDto;
import shop.goodspia.goods.entity.Goods;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GoodsDetailResponseDto {

    private Long goodsId;
    private String name;
    private String summary;
    private String mainImage;
    private String content;
    private String category;
    private int price;
    private String artistName;
    private List<DesignResponseDto> goodsDesigns;

    public GoodsDetailResponseDto(Goods goods) {
        this.goodsId = goods.getId();
        this.name = goods.getName();
        this.summary = goods.getSummary();
        this.mainImage = goods.getThumbnail();
        this.content = goods.getContent();
        this.category = goods.getCategory();
        this.price = goods.getPrice();
        this.artistName = goods.getArtist().getNickname();
        this.goodsDesigns = goods.getDesigns().stream().map(
                design -> new DesignResponseDto(design.getDesignName())
        ).collect(Collectors.toList());
    }
}
