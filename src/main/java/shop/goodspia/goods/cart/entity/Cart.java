package shop.goodspia.goods.cart.entity;

import lombok.*;
import shop.goodspia.goods.common.entity.BaseTimeEntity;
import shop.goodspia.goods.goods.entity.Design;
import shop.goodspia.goods.goods.entity.Goods;
import shop.goodspia.goods.member.entity.Member;

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
