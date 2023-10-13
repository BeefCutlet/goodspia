package shop.goodspia.goods.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.goods.entity.Goods;
import shop.goodspia.goods.member.entity.Member;
import shop.goodspia.goods.order.dto.OrderSaveListRequest;
import shop.goodspia.goods.order.dto.OrderSaveRequest;
import shop.goodspia.goods.order.dto.OrderStatus;
import shop.goodspia.goods.order.entity.OrderGoods;
import shop.goodspia.goods.order.entity.Orders;
import shop.goodspia.goods.goods.repository.GoodsRepository;
import shop.goodspia.goods.member.repository.MemberRepository;
import shop.goodspia.goods.order.repository.OrderRepository;
import shop.goodspia.goods.order.service.OrderService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
public class OrdersServiceTest {

    private OrderService orderService;
    private OrderRepository orderRepository;
    private MemberRepository memberRepository;
    private GoodsRepository goodsRepository;

//    @Test
//    void addPaymentTest() {
//        Member member = Member.builder()
//                .email("email")
//                .build();
//        Member savedMember = memberRepository.save(member);
//
//        Goods goods = Goods.builder()
//                .name("goodsName")
//                .build();
//        Goods savedGoods = goodsRepository.save(goods);
//
//        List<OrderSaveRequest> orderList = new ArrayList<>();
//        OrderSaveRequest orderGoods1 = new OrderSaveRequest(10, 100000, savedGoods.getId(), "design");
//        OrderSaveRequest orderGoods2 = new OrderSaveRequest(10, 100000, savedGoods.getId(), "design");
//        orderList.add(orderGoods1);
//        orderList.add(orderGoods2);
//        OrderSaveListRequest orderGoodsList = new OrderSaveListRequest(orderList);
//        orderService.addOrders(orderGoodsList, savedMember.getId());
//
//        List<Orders> orders = orderRepository.findAll();
//        for (Orders order : orders) {
//            assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.READY);
//            assertThat(order.getMember().getEmail()).isEqualTo("email");
//            for (OrderGoods orderGoods : order.getOrderGoods()) {
//                assertThat(orderGoods.getQuantity()).isEqualTo(10);
//                assertThat(orderGoods.getTotalPrice()).isEqualTo(100000);
//                assertThat(orderGoods.getGoods().getName()).isEqualTo("goodsName");
//            }
//        }
//    }
}
