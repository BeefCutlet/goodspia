package shop.goodspia.goods.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.goodspia.goods.dto.CartResponseDto;

import java.util.List;

import static shop.goodspia.goods.entity.QCart.cart;
import static shop.goodspia.goods.entity.QGoods.goods;

@Repository
@RequiredArgsConstructor
public class CartQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<CartResponseDto> findCartList(long memberId) {
        return queryFactory
                .select(Projections.bean(CartResponseDto.class,
                        cart.quantity.as("quantity"),
                        cart.goods.name.as("goodsName"),
                        cart.goods.price.as("price"),
                        cart.goods.image.as("image"),
                        cart.design.design.as("design")
                ))
                .from(cart)
                .join(cart.goods, goods)
                .join(cart.goods.designs)
                .where(cart.member.id.eq(memberId))
                .fetch();

    }
}
