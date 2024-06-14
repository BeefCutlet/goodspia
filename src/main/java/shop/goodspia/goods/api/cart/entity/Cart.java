package shop.goodspia.goods.api.cart.entity;

import lombok.*;
import shop.goodspia.goods.api.goods.entity.Design;
import shop.goodspia.goods.global.common.entity.BaseTimeEntity;
import shop.goodspia.goods.api.goods.entity.Goods;
import shop.goodspia.goods.api.member.entity.Member;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Cart extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;
    private int quantity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "design_id")
    private Design design;

    public static Cart from(int quantity, Member member, Goods goods, Design design) {
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
