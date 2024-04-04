package shop.goodspia.goods.goods.dto;

import lombok.Getter;
import shop.goodspia.goods.goods.dto.design.DesignResponse;
import shop.goodspia.goods.goods.entity.Goods;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GoodsDetailResponse {

    private Long goodsId;
    private String name;
    private String thumbnail;
    private String content;
    private int price;
    private String artistName;
    private List<DesignResponse> goodsDesigns;

    public GoodsDetailResponse(Goods goods) {
        this.goodsId = goods.getId();
        this.name = goods.getName();
        this.thumbnail = goods.getThumbnail();
        this.content = goods.getContent();
        this.price = goods.getPrice();
        this.artistName = goods.getArtist().getNickname();
        this.goodsDesigns = goods.getDesigns().stream().map(
                design -> new DesignResponse(design.getDesignName())
        ).collect(Collectors.toList());
    }
}
