package shop.goodspia.goods.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.goods.entity.Goods;
import shop.goodspia.goods.goods.repository.GoodsRepository;
import shop.goodspia.goods.member.entity.Member;
import shop.goodspia.goods.member.repository.MemberRepository;
import shop.goodspia.goods.order.entity.OrderGoods;
import shop.goodspia.goods.order.entity.Orders;
import shop.goodspia.goods.order.dto.*;
import shop.goodspia.goods.order.repository.OrderGoodsRepository;
import shop.goodspia.goods.order.repository.OrderQueryRepository;
import shop.goodspia.goods.order.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final GoodsRepository goodsRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final OrderGoodsRepository orderGoodsRepository;
    private final OrderQueryRepository orderQueryRepository;

    /**
     * 주문 목록에 리스트 추가
     * @param orderSaveListRequest
     */
    public Long addOrders(OrderSaveListRequest orderSaveListRequest, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        //회원의 결제되지 않은 주문이 존재하는지 조회
        Orders readyOrder = orderQueryRepository.findReadyOrderUid(memberId);
        if (readyOrder != null) {
            //기존에 결제되지 않은 주문이 존재하면 삭제
            orderRepository.deleteById(readyOrder.getId());
        }

        List<OrderGoods> orderGoodsList = new ArrayList<>();
        for (OrderSaveRequest orderSaveRequest : orderSaveListRequest.getOrders()) {
            Goods goods = goodsRepository.findById(orderSaveRequest.getGoodsId())
                    .orElseThrow(() -> new IllegalArgumentException("굿즈 정보를 찾을 수 없습니다. : "
                            + orderSaveRequest.getGoodsId()));
            OrderGoods orderGoods = OrderGoods
                    .createOrderGoods(
                            goods,
                            orderSaveRequest.getQuantity(),
                            orderSaveRequest.getTotalPrice(),
                            orderSaveRequest.getGoodsDesign());
            orderGoodsList.add(orderGoods);
        }

        Orders orders = Orders.createOrder(member, orderGoodsList);
        Orders savedOrder = orderRepository.save(orders);
        return savedOrder.getId();
    }

    public void removeOrder(Long orderGoodsId) {
        orderGoodsRepository.deleteById(orderGoodsId);
    }

    //현재 주문 목록 조회
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

    //아티스트에게 들어온 주문 목록 조회
    public OrderPageResponse<OrderReceivedResponse> getReceivedOrders(Long artistId, Pageable pageable) {
        Page<OrderReceivedResponse> orderGoods = orderQueryRepository.findArtistOrderGoodsList(artistId, pageable);
        if (orderGoods == null || !orderGoods.hasContent()) {
            throw new IllegalArgumentException("주문 정보를 찾을 수 없습니다.");
        }
        return new OrderPageResponse<>(orderGoods.getContent(), orderGoods.getTotalPages());
    }

    //회원이 주문했던 주문 목록
    public OrderPageResponse<OrderResponse> getCompleteOrders(Long memberId, Pageable pageable) {
        Page<OrderResponse> completeOrders = orderQueryRepository.findCompleteOrders(memberId, pageable);
        if (completeOrders == null || !completeOrders.hasContent()) {
            throw new IllegalArgumentException("접수한 주문이 없습니다.");
        }
        return new OrderPageResponse<>(completeOrders.getContent(), completeOrders.getTotalPages());
    }

    //회원이 주문했던 주문 (단건)
    public OrderDetailResponse getOrderDetail(Long orderGoodsId) {
        OrderGoods orderDetail = orderQueryRepository.findOrderDetail(orderGoodsId);
        if (orderDetail == null) {
            throw new IllegalArgumentException("굿즈 정보가 존재하지 않습니다.");
        }
        return new OrderDetailResponse(orderDetail);
    }
}
