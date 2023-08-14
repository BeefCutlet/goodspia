package shop.goodspia.goods.entity;

import lombok.*;
import shop.goodspia.goods.dto.GoodsDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Goods extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "goods_id")
    private Long id;
    private String name;
    private String summary;
    private String content;
    private String category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @OneToMany(mappedBy = "goods")
    private final List<Design> designs = new ArrayList<>();

    public static Goods createGoods(GoodsDto goodsDto, Artist artist) {
        return Goods.builder()
                .name(goodsDto.getName())
                .summary(goodsDto.getSummary())
                .content(goodsDto.getContent())
                .category(goodsDto.getCategory())
                .artist(artist)
                .build();
    }
}
