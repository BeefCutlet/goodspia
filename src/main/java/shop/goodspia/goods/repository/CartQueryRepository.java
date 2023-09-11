package shop.goodspia.goods.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
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

    public Page<CartResponse> findCartList(Long memberId, Pageable pageable) {
        List<CartResponse> cartList = queryFactory
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
                .orderBy(cart.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(cart.count())
                .from(cart)
                .join(cart.goods, goods)
                .join(cart.design, design)
                .where(cart.member.id.eq(memberId));

        return PageableExecutionUtils.getPage(cartList, pageable, countQuery::fetchOne);
    }
}
