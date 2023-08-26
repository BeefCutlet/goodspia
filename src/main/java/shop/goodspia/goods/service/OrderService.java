package shop.goodspia.goods.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.dto.order.OrderListRequestDto;
import shop.goodspia.goods.dto.order.OrderRequestDto;
import shop.goodspia.goods.entity.Goods;
import shop.goodspia.goods.entity.Member;
import shop.goodspia.goods.entity.OrderGoods;
import shop.goodspia.goods.entity.Orders;
import shop.goodspia.goods.exception.GoodsNotFoundException;
import shop.goodspia.goods.exception.MemberNotFoundException;
import shop.goodspia.goods.repository.GoodsRepository;
import shop.goodspia.goods.repository.MemberRepository;
import shop.goodspia.goods.repository.OrderGoodsRepository;
import shop.goodspia.goods.repository.OrderRepository;

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

    /**
     * 주문 목록에 리스트 추가
     * @param orderListRequestDto
     */
    public void addOrders(OrderListRequestDto orderListRequestDto, long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member Data Not Found"));

        List<OrderGoods> orderGoodsList = new ArrayList<>();
        for (OrderRequestDto orderRequestDto : orderListRequestDto.getOrders()) {
            Goods goods = goodsRepository.findById(orderRequestDto.getGoodsId())
                    .orElseThrow(() -> new GoodsNotFoundException("Goods Data Not Found, try to find ID : "
                            + orderRequestDto.getGoodsId()));
            OrderGoods orderGoods = OrderGoods
                    .createOrderGoods(
                            goods,
                            orderRequestDto.getQuantity(),
                            orderRequestDto.getTotalPrice());
            orderGoodsList.add(orderGoods);
        }

        Orders orders = Orders.createOrder(member, orderGoodsList);
        orderRepository.save(orders);
    }

    public void removeOrder(Long orderGoodsId) {
        orderGoodsRepository.deleteById(orderGoodsId);
    }
}
