package shop.goodspia.goods.goods.dto;

import lombok.Getter;
import shop.goodspia.goods.goods.dto.design.DesignResponse;
import shop.goodspia.goods.goods.entity.Goods;

import javax.validation.constraints.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GoodsDetailResponse {

    @Min(1)
    private Long goodsId;

    @NotNull
    @Pattern(regexp = "[0-9a-zA-Z가-힣]")
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    private String summary;

    private String mainImage;

    @NotNull
    private String content;

    @NotNull
    private String category;

    @Min(1000)
    @Max(10000000)
    private int price;

    @NotNull
    @Pattern(regexp = "[a-zA-Z가-힣]")
    private String artistName;

    private List<DesignResponse> goodsDesigns;

    public GoodsDetailResponse(Goods goods) {
        this.goodsId = goods.getId();
        this.name = goods.getName();
        this.summary = goods.getSummary();
        this.mainImage = goods.getThumbnail();
        this.content = goods.getContent();
        this.category = goods.getCategory();
        this.price = goods.getPrice();
        this.artistName = goods.getArtist().getNickname();
        this.goodsDesigns = goods.getDesigns().stream().map(
                design -> new DesignResponse(design.getDesignName())
        ).collect(Collectors.toList());
    }
}
