package shop.goodspia.goods.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import shop.goodspia.goods.cart.repository.CartQueryRepository;
import shop.goodspia.goods.goods.repository.GoodsQueryRepository;
import shop.goodspia.goods.order.repository.OrderQueryRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@TestConfiguration
public class RepositoryTestConfiguration {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory queryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public OrderQueryRepository orderQueryRepository() {
        return new OrderQueryRepository(entityManager);
    }

    @Bean
    public CartQueryRepository cartQueryRepository() {
        return new CartQueryRepository(entityManager);
    }

    @Bean
    public GoodsQueryRepository goodsQueryRepository() {
        return new GoodsQueryRepository(entityManager);
    }
}
