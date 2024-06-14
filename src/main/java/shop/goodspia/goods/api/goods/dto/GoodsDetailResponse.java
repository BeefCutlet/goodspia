package shop.goodspia.goods.api.goods.dto;

import lombok.Getter;
import shop.goodspia.goods.api.goods.dto.design.DesignResponse;
import shop.goodspia.goods.api.goods.entity.Goods;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GoodsDetailResponse {

    private final Long goodsId;
    private final String name;
    private final String thumbnail;
    private final String content;
    private final int price;
    private final String material;
    private final String size;
    private final int wishCount;
    private final String artistName;
    private final List<DesignResponse> goodsDesigns;

    private GoodsDetailResponse(Goods goods) {
        this.goodsId = goods.getId();
        this.name = goods.getName();
        this.thumbnail = goods.getThumbnail();
        this.content = goods.getContent();
        this.price = goods.getPrice();
        this.material = goods.getMaterial();
        this.size = goods.getSize();
        this.wishCount = goods.getWishCount();
        this.artistName = goods.getArtist().getNickname();
        this.goodsDesigns = goods.getDesigns().stream().map(
                design -> new DesignResponse(design.getId(), design.getDesignName())
        ).collect(Collectors.toList());
    }

    public static GoodsDetailResponse from(Goods goods) {
        return new GoodsDetailResponse(goods);
    }
}
