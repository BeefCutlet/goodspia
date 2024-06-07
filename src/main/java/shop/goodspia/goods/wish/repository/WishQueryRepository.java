package shop.goodspia.goods.wish.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import shop.goodspia.goods.artist.entity.QArtist;
import shop.goodspia.goods.goods.entity.QGoods;
import shop.goodspia.goods.wish.dto.WishResponse;
import shop.goodspia.goods.wish.entity.Wish;

import javax.persistence.EntityManager;

import java.util.List;

import static shop.goodspia.goods.artist.entity.QArtist.*;
import static shop.goodspia.goods.goods.entity.QGoods.*;
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

    /**
     * 사용자가 찜한 굿즈 목록 조회
     */
    public List<WishResponse> getWishList(Long memberId) {
        return queryFactory
                .select(Projections.bean(WishResponse.class,
                        wish.goods.id.as("goodsId"),
                        wish.goods.thumbnail.as("thumbnail"),
                        wish.goods.name.as("goodsName"),
                        wish.goods.price.as("price"),
                        wish.goods.artist.nickname.as("artistNickname")
                ))
                .from(wish)
                .join(wish.goods, goods)
                .join(wish.goods.artist, artist)
                .where(wish.member.id.eq(memberId))
                .orderBy(wish.id.desc())
                .fetch();
    }
}
