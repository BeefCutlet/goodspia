package shop.goodspia.goods.goods.entity;

import lombok.*;
import shop.goodspia.goods.artist.entity.Artist;
import shop.goodspia.goods.common.entity.BaseTimeEntity;
import shop.goodspia.goods.goods.dto.GoodsSaveRequest;
import shop.goodspia.goods.goods.dto.GoodsUpdateRequest;

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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long id;
    private String name;
    private String summary;
    private String content;
    private String category;
    private String thumbnail;
    private int price;
    private int isDeleted;
    private LocalDateTime deletedTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL)
    private final List<Design> designs = new ArrayList<>();

    public static Goods createGoods(GoodsSaveRequest goodsSaveRequest, Artist artist) {
        return Goods.builder()
                .name(goodsSaveRequest.getName())
                .summary(goodsSaveRequest.getSummary())
                .content(goodsSaveRequest.getContent())
                .category(goodsSaveRequest.getCategory())
                .thumbnail(goodsSaveRequest.getThumbnail())
                .price(goodsSaveRequest.getPrice())
                .isDeleted(0)
                .artist(artist)
                .build();
    }

    //굿즈 정보 갱신용 메서드
    public void updateGoods(GoodsUpdateRequest goodsUpdateRequest) {
        this.name = goodsUpdateRequest.getName();
        this.summary = goodsUpdateRequest.getSummary();
        this.content = goodsUpdateRequest.getContent();
        this.category = goodsUpdateRequest.getCategory();
        this.thumbnail = goodsUpdateRequest.getThumbnail();
        this.price = goodsUpdateRequest.getPrice();
    }

    //굿즈 디자인 추가 메서드
    public void addDesign(List<Design> designs) {
        this.designs.addAll(designs);
    }

    //굿즈 삭제용 메서드 - 삭제 여부 상태 변경
    public void delete() {
        this.isDeleted = 1;
        this.deletedTime = LocalDateTime.now();
    }
}
