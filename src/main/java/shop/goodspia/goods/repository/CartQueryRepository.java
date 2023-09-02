package shop.goodspia.goods.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import shop.goodspia.goods.dto.cart.CartResponse;

import javax.persistence.EntityManager;
import java.util.List;

import static shop.goodspia.goods.entity.QCart.cart;
import static shop.goodspia.goods.entity.QDesign.design;
import static shop.goodspia.goods.entity.QGoods.goods;

@Repository
public class CartQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CartQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<CartResponse> findCartList(long memberId) {
        return queryFactory
                .select(Projections.bean(CartResponse.class,
                        cart.quantity.as("quantity"),
                        cart.goods.name.as("goodsName"),
                        cart.goods.price.as("price"),
                        cart.goods.thumbnail.as("mainImage"),
                        cart.design.designName.as("designName")
                ))
                .from(cart)
                .join(cart.goods, goods)
                .join(cart.design, design)
                .where(cart.member.id.eq(memberId))
                .fetch();

    }
}
