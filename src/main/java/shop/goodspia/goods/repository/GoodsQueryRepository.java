package shop.goodspia.goods.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import shop.goodspia.goods.dto.goods.GoodsDetailResponseDto;
import shop.goodspia.goods.dto.goods.GoodsResponseDto;

import javax.persistence.EntityManager;
import java.util.List;

import static shop.goodspia.goods.entity.QArtist.artist;
import static shop.goodspia.goods.entity.QGoods.goods;

@Repository
public class GoodsQueryRepository {

    private final JPAQueryFactory queryFactory;

    public GoodsQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    //굿즈 리스트 페이징 조회
    public Page<GoodsResponseDto> findGoodsList(Pageable pageable) {
        List<GoodsResponseDto> goodsList = queryFactory
                .select(Projections.bean(GoodsResponseDto.class,
                        goods.id.as("goodsId"),
                        goods.name.as("goodsName"),
                        goods.price.as("price"),
                        goods.image.as("image"),
                        goods.artist.nickname.as("artistName")))
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

    //굿즈 단건 조회
    public GoodsDetailResponseDto findGoodsById(long goodsId) {
        return queryFactory
                .select(Projections.bean(GoodsDetailResponseDto.class,
                        goods.id.as("goodsId"),
                        goods.name.as("name"),
                        goods.summary.as("summary"),
                        goods.image.as("mainImage"),
                        goods.content.as("content"),
                        goods.category.as("category"),
                        goods.price.as("price"),
                        goods.artist.nickname.as("artistName")))
                .from(goods)
                .join(goods.artist, artist)
                .where(goods.id.eq(goodsId))
                .fetchOne();
    }

    //굿즈 리스트 페이징 조회
    public Page<GoodsResponseDto> findArtistGoodsList(Pageable pageable, long artistId) {
        List<GoodsResponseDto> goodsList = queryFactory
                .select(Projections.bean(GoodsResponseDto.class,
                        goods.id.as("goodsId"),
                        goods.name.as("goodsName"),
                        goods.price.as("price"),
                        goods.image.as("image")))
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
