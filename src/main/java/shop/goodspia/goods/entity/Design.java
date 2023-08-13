package shop.goodspia.goods.entity;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
public class Design {

    @Id
    @GeneratedValue
    @Column(name = "design_id")
    private Long id;
    private String design;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;
}
