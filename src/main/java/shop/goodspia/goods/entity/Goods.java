package shop.goodspia.goods.entity;

import lombok.*;
import shop.goodspia.goods.dto.GoodsDto;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private String image;
    private int price;
    private int isDeleted;
    private LocalDateTime deletedTime;

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
                .image(goodsDto.getImage())
                .price(goodsDto.getPrice())
                .isDeleted(0)
                .artist(artist)
                .build();
    }

    //굿즈 정보 갱신용 메서드
    public void updateGoods(GoodsDto goodsDto) {
        this.name = goodsDto.getName();
        this.summary = goodsDto.getSummary();
        this.content = goodsDto.getContent();
        this.category = goodsDto.getCategory();
        this.image = goodsDto.getImage();
        this.price = goodsDto.getPrice();
    }

    //굿즈 삭제용 메서드 - 삭제 여부 상태 변경
    public void delete() {
        this.isDeleted = 0;
        this.deletedTime = LocalDateTime.now();
    }
}
