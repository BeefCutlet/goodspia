package shop.goodspia.goods.entity;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
public class Cart {

    @Id @GeneratedValue
    @Column(name = "cart_id")
    private Long id;
    private int quantity;

    @ManyToOne(fetch = LAZY)
    private Member member;
    @ManyToOne(fetch = LAZY)
    private Goods goods;
}
