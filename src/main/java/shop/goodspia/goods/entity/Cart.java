package shop.goodspia.goods.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Cart extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "cart_id")
    private Long id;
    private int quantity;

    @ManyToOne(fetch = LAZY)
    private Member member;
    @ManyToOne(fetch = LAZY)
    private Goods goods;
    @ManyToOne(fetch = LAZY)
    private Design design;

    public static Cart createCart(int quantity, Member member, Goods goods, Design design) {
        return Cart.builder()
                .quantity(quantity)
                .member(member)
                .goods(goods)
                .design(design)
                .build();
    }

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }
}
