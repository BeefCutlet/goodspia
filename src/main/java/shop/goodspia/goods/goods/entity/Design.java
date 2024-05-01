package shop.goodspia.goods.goods.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Design {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "design_id")
    private Long id;
    private String designName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    public static Design from(String design, Goods goods) {
        return Design.builder()
                .designName(design)
                .goods(goods)
                .build();
    }

    public void modifyDesignName(String designName) {
        this.designName = designName;
    }
}
