package shop.goodspia.goods.cart.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import shop.goodspia.goods.cart.dto.CartResponse;

import javax.persistence.EntityManager;
import java.util.List;

import static shop.goodspia.goods.artist.entity.QArtist.artist;
import static shop.goodspia.goods.cart.entity.QCart.cart;
import static shop.goodspia.goods.goods.entity.QDesign.design;
import static shop.goodspia.goods.goods.entity.QGoods.goods;


@Repository
public class CartQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CartQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<CartResponse> findCartList(Long memberId) {
        return queryFactory
                .select(Projections.bean(CartResponse.class,
                        cart.id.as("cartId"),
                        cart.quantity.as("quantity"),
                        cart.goods.name.as("goodsName"),
                        cart.goods.price.as("price"),
                        cart.goods.thumbnail.as("thumbnail"),
                        cart.design.designName.as("designName"),
                        cart.goods.artist.nickname.as("artistNickname")
                ))
                .from(cart)
                .join(cart.goods, goods)
                .join(cart.goods.artist, artist)
                .join(cart.design, design)
                .where(cart.member.id.eq(memberId))
                .orderBy(cart.id.desc())
                .fetch();
    }
}
