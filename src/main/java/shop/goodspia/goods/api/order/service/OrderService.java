package shop.goodspia.goods.api.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.api.artist.entity.Artist;
import shop.goodspia.goods.api.artist.repository.ArtistRepository;
import shop.goodspia.goods.api.coupon.entity.Coupon;
import shop.goodspia.goods.api.coupon.repository.CouponQueryRepository;
import shop.goodspia.goods.api.coupon.service.CouponDiscountCalculator;
import shop.goodspia.goods.api.goods.repository.GoodsRepository;
import shop.goodspia.goods.api.member.entity.Member;
import shop.goodspia.goods.api.order.dto.*;
import shop.goodspia.goods.api.order.repository.OrderQueryRepository;
import shop.goodspia.goods.api.order.repository.OrderRepository;
import shop.goodspia.goods.api.goods.entity.Goods;
import shop.goodspia.goods.api.member.repository.MemberRepository;
import shop.goodspia.goods.api.order.entity.OrderGoods;
import shop.goodspia.goods.api.order.entity.Orders;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final GoodsRepository goodsRepository;
    private final MemberRepository memberRepository;
    private final ArtistRepository artistRepository;
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final CouponQueryRepository couponQueryRepository;

    private final CouponDiscountCalculator couponDiscountCalculator;

    /**
     * 주문 목록에 리스트 추가
     */
    public OrderSaveResponse addOrders(OrderSaveListRequest orderSaveListRequest, Long memberId) {
        Member member = getMember(memberId);

        //회원의 결제되지 않은 주문이 존재하는지 조회
        Orders readyOrder = orderQueryRepository.findReadyOrderUid(memberId);
        if (readyOrder != null) {
            //기존에 결제되지 않은 주문이 존재하면 삭제
            orderRepository.delete(readyOrder);
        }

        int orderPrice = 0;
        List<OrderGoods> orderGoodsList = new ArrayList<>();
        orderPrice = getOrderPrice(orderSaveListRequest, orderPrice, orderGoodsList);

        //쿠폰 할인 적용
        orderPrice = applyCouponDiscount(orderSaveListRequest, memberId, orderPrice);

        //주문 생성
        Orders orders = Orders.of(member, orderGoodsList, orderPrice);
        Orders savedOrder = orderRepository.save(orders);

        log.info("[SUCCESS] SavedOrderID: {}, SavedOrderPrice: {}", savedOrder.getId(), savedOrder.getOrderPrice());
        return OrderSaveResponse.from(savedOrder);
    }

    private int applyCouponDiscount(final OrderSaveListRequest orderSaveListRequest, final Long memberId, int orderPrice) {
        Coupon foundCoupon = couponQueryRepository.findReceivedCoupon(memberId, orderSaveListRequest.getCouponId());
        orderPrice = couponDiscountCalculator.discount(orderPrice, foundCoupon);
        return orderPrice;
    }

    private Member getMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
    }

    private int getOrderPrice(final OrderSaveListRequest orderSaveListRequest, int orderPrice, final List<OrderGoods> orderGoodsList) {
        for (OrderSaveRequest orderSaveRequest : orderSaveListRequest.getOrders()) {
            Goods goods = goodsRepository.findById(orderSaveRequest.getGoodsId())
                    .orElseThrow(() -> new IllegalArgumentException("굿즈 정보를 찾을 수 없습니다. : "
                            + orderSaveRequest.getGoodsId()));
            OrderGoods orderGoods = OrderGoods.from(
                            goods,
                            orderSaveRequest.getQuantity(),
                            orderSaveRequest.getTotalPrice(),
                            orderSaveRequest.getGoodsDesign());
            orderGoodsList.add(orderGoods);
            orderPrice += orderSaveRequest.getTotalPrice();
        }
        return orderPrice;
    }

    /**
     * 주문 정보 및 주문 굿즈 정보 삭제
     */
    public void removeOrder(Long orderId) {
        Orders foundOrder = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new IllegalArgumentException("주문 정보가 존재하지 않습니다.");
        });
        orderRepository.delete(foundOrder);
    }

    /**
     * 현재 주문 목록 조회
     */
    public OrderPageResponse<OrderResponse> getRequestedOrders(Long memberId, Pageable pageable) {
        //아직 결제되지 않은 상품 리스트 조회
        Page<OrderGoods> readyOrders = orderQueryRepository.findReadyOrders(memberId, pageable);
        List<OrderResponse> orders = new ArrayList<>();
        for (OrderGoods readyOrder : readyOrders) {
            OrderResponse order = new OrderResponse(readyOrder);
            orders.add(order);
        }

        return new OrderPageResponse<>(orders, readyOrders.getTotalPages());
    }

    /**
     * 아티스트에게 들어온 주문 목록 조회
     */
    public OrderPageResponse<OrderReceivedResponse> getReceivedOrders(Long memberId, Pageable pageable, String year, String month) {
        Artist foundArtist = artistRepository.findArtistByMemberId(memberId).orElseThrow(() -> {
            throw new IllegalArgumentException("아티스트 정보가 존재하지 않습니다.");
        });

        Page<OrderReceivedResponse> orderGoods = orderQueryRepository.findArtistOrderGoodsList(foundArtist.getId(), pageable, year, month);
        if (orderGoods == null || !orderGoods.hasContent()) {
            throw new IllegalArgumentException("주문 정보를 찾을 수 없습니다.");
        }
        return new OrderPageResponse<>(orderGoods.getContent(), orderGoods.getTotalPages());
    }

    /**
     * 회원이 주문했던 주문 목록
     */
    public OrderPageResponse<OrderResponse> getCompleteOrders(Long memberId, Pageable pageable) {
        Page<OrderResponse> completeOrders = orderQueryRepository.findCompleteOrders(memberId, pageable);
        if (completeOrders == null || !completeOrders.hasContent()) {
            throw new IllegalArgumentException("접수한 주문이 없습니다.");
        }
        return new OrderPageResponse<>(completeOrders.getContent(), completeOrders.getTotalPages());
    }

    /**
     * 회원이 주문했던 주문 (단건)
     */
    public OrderDetailResponse getOrderDetail(Long orderGoodsId) {
        OrderGoods orderDetail = orderQueryRepository.findOrderDetail(orderGoodsId);
        if (orderDetail == null) {
            throw new IllegalArgumentException("굿즈 정보가 존재하지 않습니다.");
        }
        return new OrderDetailResponse(orderDetail);
    }
}
