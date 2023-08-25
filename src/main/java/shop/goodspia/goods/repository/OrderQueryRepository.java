package shop.goodspia.goods.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static shop.goodspia.goods.entity.QOrderGoods.orderGoods;
import static shop.goodspia.goods.entity.QOrders.orders;

@Repository
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    public OrderQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Long findTotalPrice(String orderUid) {
        return queryFactory
                .select(orderGoods.totalPrice.count())
                .from(orderGoods)
                .join(orders)
                .where(orders.orderUid.eq(orderUid))
                .fetchOne();
    }
}
