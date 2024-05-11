package shop.goodspia.goods.wish.entity;

import lombok.*;
import shop.goodspia.goods.goods.entity.Goods;
import shop.goodspia.goods.member.entity.Member;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Wish {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    //Wish 엔티티 생성
    public static Wish from(Member member, Goods goods) {
        return Wish.builder()
                .member(member)
                .goods(goods)
                .build();
    }
}
