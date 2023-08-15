package shop.goodspia.goods.entity;

import lombok.*;
import shop.goodspia.goods.dto.CartDto;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Design {

    @Id
    @GeneratedValue
    @Column(name = "design_id")
    private Long id;
    private String design;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    public static Design createDesign(String design, Goods goods) {
        return Design.builder()
                .design(design)
                .goods(goods)
                .build();
    }
}
