package shop.goodspia.goods.goods.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import shop.goodspia.goods.goods.dto.GoodsResponse;
import shop.goodspia.goods.goods.entity.Goods;

import javax.persistence.EntityManager;
import java.util.List;

import static shop.goodspia.goods.artist.entity.QArtist.artist;
import static shop.goodspia.goods.goods.entity.QGoods.goods;

@Repository
public class GoodsQueryRepository {

    private final JPAQueryFactory queryFactory;

    public GoodsQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 굿즈 리스트 페이징 조회 (카테고리 입력 시 카테고리 분류)
     */
    public Page<GoodsResponse> findGoodsList(Pageable pageable) {
        List<GoodsResponse> goodsList = queryFactory
                .select(Projections.bean(GoodsResponse.class,
                        goods.id.as("goodsId"),
                        goods.name.as("goodsName"),
                        goods.price.as("price"),
                        goods.thumbnail.as("thumbnail"),
                        goods.artist.nickname.as("artistNickname")))
                .from(goods)
                .join(goods.artist, artist)
                .orderBy(goods.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(goods.count())
                .from(goods)
                .join(goods.artist, artist);

        return PageableExecutionUtils.getPage(goodsList, pageable, countQuery::fetchOne);
    }

    /**
     * 굿즈 단건 조회
     */
    public Goods findGoodsDetail(Long goodsId) {
        return queryFactory
                .select(goods)
                .from(goods)
                .join(goods.artist, artist)
                .where(goods.id.eq(goodsId))
                .fetchOne();
    }

    //아티스트가 등록한 굿즈 리스트 페이징 조회
    public Page<GoodsResponse> findArtistGoodsList(Pageable pageable, Long artistId) {
        List<GoodsResponse> goodsList = queryFactory
                .select(Projections.bean(GoodsResponse.class,
                        goods.id.as("goodsId"),
                        goods.name.as("goodsName"),
                        goods.price.as("price"),
                        goods.thumbnail.as("image")))
                .from(goods)
                .join(goods.artist, artist)
                .where(goods.artist.id.eq(artistId))
                .orderBy(goods.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(goods.count())
                .from(goods)
                .join(goods.artist, artist)
                .where(goods.artist.id.eq(artistId));

        return PageableExecutionUtils.getPage(goodsList, pageable, countQuery::fetchOne);
    }
}
