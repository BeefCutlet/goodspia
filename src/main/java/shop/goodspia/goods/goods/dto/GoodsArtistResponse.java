package shop.goodspia.goods.goods.dto;

import lombok.*;
import shop.goodspia.goods.goods.dto.design.DesignResponse;
import shop.goodspia.goods.goods.entity.Design;
import shop.goodspia.goods.goods.entity.Goods;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GoodsArtistResponse {

    private Long goodsId;
    private String goodsName;
    private String content;
    private int price;
    private int stock;
    private int wishCount;
    private String material;
    private String size;
    private String thumbnail;
    private int isLimited;
    private List<DesignResponse> designs;

    public static GoodsArtistResponse from(Goods goods, List<Design> designs) {
        return GoodsArtistResponse.builder()
                .goodsId(goods.getId())
                .goodsName(goods.getName())
                .content(goods.getContent())
                .price(goods.getPrice())
                .stock(goods.getStock())
                .wishCount(goods.getWishCount())
                .material(goods.getMaterial())
                .size(goods.getSize())
                .thumbnail(goods.getThumbnail())
                .isLimited(goods.getIsLimited())
                .designs(designs.stream().map((design) -> new DesignResponse(design.getId(), design.getDesignName()))
                        .collect(Collectors.toList()))
                .build();
    }
}
