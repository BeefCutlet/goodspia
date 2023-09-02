package shop.goodspia.goods.entity;

import lombok.*;
import shop.goodspia.goods.dto.goods.GoodsSaveRequest;
import shop.goodspia.goods.dto.goods.GoodsUpdateRequest;

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
    private String thumbnail;
    private int price;
    private int isDeleted;
    private LocalDateTime deletedTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @OneToMany(mappedBy = "goods")
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

    //굿즈 삭제용 메서드 - 삭제 여부 상태 변경
    public void delete() {
        this.isDeleted = 0;
        this.deletedTime = LocalDateTime.now();
    }
}
