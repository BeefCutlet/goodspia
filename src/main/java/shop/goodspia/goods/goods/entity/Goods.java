package shop.goodspia.goods.goods.entity;

import lombok.*;
import org.springframework.util.StringUtils;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long id;
    private String name;
    private String content;
    private int price;
    private int stock;
    private int wishCount;
    private String material;
    private String size;
    private String thumbnail;
    private int isLimited;
    private int isDeleted;
    private LocalDateTime deletedTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL)
    private List<Design> designs = new ArrayList<>();

    public static Goods from(GoodsSaveRequest goodsSaveRequest, Artist artist, String content, String thumbnail) {
        return Goods.builder()
                .name(goodsSaveRequest.getName())
                .content(content)
                .price(goodsSaveRequest.getPrice())
                .stock(goodsSaveRequest.getStock())
                .wishCount(0)
                .material(goodsSaveRequest.getMaterial())
                .size(goodsSaveRequest.getSize())
                .thumbnail(thumbnail)
                .isLimited(goodsSaveRequest.getIsLimited() ? 1 : 0)
                .isDeleted(0)
                .artist(artist)
                .build();
    }

    //굿즈 정보 갱신용 메서드
    public void updateGoods(GoodsUpdateRequest goodsUpdateRequest, String thumbnailImagePath, String contentImagePath) {
        this.name = goodsUpdateRequest.getName();
        this.price = goodsUpdateRequest.getPrice();
        this.stock = goodsUpdateRequest.getStock();
        this.material = goodsUpdateRequest.getMaterial();
        this.size = goodsUpdateRequest.getSize();
        this.isLimited = goodsUpdateRequest.getIsLimited() ? 1 : 0;

        if (StringUtils.hasText(thumbnailImagePath)) {
            this.thumbnail = thumbnailImagePath;
        }

        if (StringUtils.hasText(contentImagePath)) {
            this.content = contentImagePath;
        }
    }

    //굿즈 디자인 추가 메서드
    public void addDesign(List<Design> designs) {
        this.designs = designs;
    }

    //굿즈 삭제용 메서드 - 삭제 여부 상태 변경
    public void delete() {
        this.isDeleted = 1;
        this.deletedTime = LocalDateTime.now();
    }

    //찜한 사람 수 검증 - 0개 이상이면 true, 0개 이하면 false
    public boolean validateWishCount() {
        return this.wishCount >= 0;
    }

    //찜한 사람 수 증가
    public void increaseWishCount() {
        this.wishCount++;
    }

    //찜한 사람 수 감소
    public void decreaseWishCount() {
        this.wishCount--;
    }
}
