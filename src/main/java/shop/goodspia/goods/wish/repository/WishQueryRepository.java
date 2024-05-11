package shop.goodspia.goods.wish.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import shop.goodspia.goods.wish.entity.Wish;

import javax.persistence.EntityManager;

import static shop.goodspia.goods.wish.entity.QWish.wish;

@Repository
public class WishQueryRepository {

    private final JPAQueryFactory queryFactory;

    public WishQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 매개변수로 들어온 굿즈 리스트 중에 현재 사용자가 찜한 단건 조회
     */
    public Wish findWish(Long memberId, Long goodsId) {
        return queryFactory.select(wish)
                .from(wish)
                .where(wish.member.id.eq(memberId).and(wish.goods.id.eq(goodsId)))
                .fetchOne();
    }
}
