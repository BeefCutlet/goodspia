package shop.goodspia.goods.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import shop.goodspia.goods.dto.order.OrderReceivedResponseDto;
import shop.goodspia.goods.dto.order.OrderResponseDto;
import shop.goodspia.goods.entity.OrderGoods;
import shop.goodspia.goods.entity.OrderStatus;
import shop.goodspia.goods.entity.Orders;
import shop.goodspia.goods.entity.PaymentStatus;

import javax.persistence.EntityManager;
import java.util.List;

import static shop.goodspia.goods.entity.QDelivery.delivery;
import static shop.goodspia.goods.entity.QGoods.goods;
import static shop.goodspia.goods.entity.QMember.member;
import static shop.goodspia.goods.entity.QOrderGoods.orderGoods;
import static shop.goodspia.goods.entity.QOrders.orders;
import static shop.goodspia.goods.entity.QPayments.payments;

@Repository
public class OrderQueryRepository {
    private final JPAQueryFactory queryFactory;

    public OrderQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    //회원이 결제하지 않은 주문 상품들의 전체 가격
    public Long findTotalPrice(String orderUid) {
        return queryFactory
                .select(orderGoods.totalPrice.count())
                .from(orderGoods)
                .join(orders)
                .where(orders.orderUid.eq(orderUid))
                .fetchOne();
    }

    //회원이 결제하지 않은 상품들 목록
    public List<OrderGoods> findReadyOrders(Long memberId) {
        return queryFactory
                .select(orderGoods)
                .from(orderGoods)
                .join(orderGoods.orders, orders)
                .join(orderGoods.goods, goods)
                .where(orders.member.id.eq(memberId), orders.orderStatus.eq(OrderStatus.READY))
                .fetch();
    }

    //회원이 결제하지 않은 주문 여부 확인
    public Orders findReadyOrderUid(Long memberId) {
        return queryFactory
                .select(orders)
                .from(orders)
                .where(orders.member.id.eq(memberId), orders.orderStatus.eq(OrderStatus.READY))
                .fetchOne();
    }

    //아티스트가 받은 주문 목록 조회
    public Page<OrderReceivedResponseDto> findArtistOrderGoodsList(Long artistId, Pageable pageable) {
        List<OrderReceivedResponseDto> orderGoodsList = queryFactory
                .select(Projections.fields(OrderReceivedResponseDto.class,
                        goods.id.as("goodsId"),
                        goods.name.as("goodsName"),
                        goods.designs.as("goodsDesign"),
                        goods.price.as("goodsPrice"),
                        orders.member.nickname.as("memberNickname"),
                        payments.createdTime.as("paymentTime"),
                        orderGoods.quantity.as("quantity"),
                        orderGoods.totalPrice.as("totalPrice"),
                        orderGoods.orders.delivery.address.as("address")
                ))
                .from(orderGoods)
                .join(orderGoods.goods, goods)
                .join(orderGoods.orders, orders)
                .join(orderGoods.orders.payments, payments)
                .join(orderGoods.orders.delivery, delivery)
                .where(goods.artist.id.eq(artistId), payments.paymentStatus.eq(PaymentStatus.COMPLETE))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(orderGoods.count())
                .from(orderGoods)
                .join(orderGoods.goods, goods)
                .join(orderGoods.orders, orders)
                .join(orderGoods.orders.payments, payments)
                .join(orderGoods.orders.delivery, delivery)
                .where(goods.artist.id.eq(artistId), payments.paymentStatus.eq(PaymentStatus.COMPLETE));

        return PageableExecutionUtils.getPage(orderGoodsList, pageable, countQuery::fetchOne);
    }

    //회원이 주문 완료한 굿즈 목록
    public Page<OrderResponseDto> findCompleteOrders(Long memberId, Pageable pageable) {
        List<OrderResponseDto> orderList = queryFactory
                .select(Projections.fields(OrderResponseDto.class,
                        orderGoods.id.as("orderGoodsId"),
                        orderGoods.quantity.as("quantity"),
                        orderGoods.totalPrice.as("totalPrice"),
                        goods.id.as("goodsId"),
                        goods.name.as("goodsName"),
                        goods.price.as("goodsPrice"),
                        goods.thumbnail.as("goodsImage"),
                        goods.designs.as("goodsDesign")
                ))
                .from(orderGoods)
                .join(orderGoods.goods, goods)
                .join(orderGoods.orders.member, member)
                .where(member.id.eq(memberId) , orders.orderStatus.eq(OrderStatus.COMPLETE))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(orderGoods.count())
                .from(orderGoods)
                .join(orderGoods.goods, goods)
                .join(orderGoods.orders.member, member)
                .where(orderGoods.orders.member.id.eq(memberId));

        return PageableExecutionUtils.getPage(orderList, pageable, countQuery::fetchOne);
    }

    //회원이 주문했던 상품 조회 (단건)
    public OrderGoods findOrderDetail(Long orderGoodsId) {
        return queryFactory
                .select(orderGoods)
                .from(orderGoods)
                .join(orderGoods.goods, goods)
                .join(orderGoods.orders, orders)
                .join(orderGoods.orders.delivery, delivery)
                .where(orderGoods.id.eq(orderGoodsId))
                .fetchOne();
    }
}
