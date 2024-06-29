package shop.goodspia.goods.global.test.data;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.api.goods.entity.Goods;
import shop.goodspia.goods.api.member.entity.Member;
import shop.goodspia.goods.api.order.dto.OrderStatus;
import shop.goodspia.goods.api.order.entity.OrderGoods;
import shop.goodspia.goods.api.order.entity.Orders;
import shop.goodspia.goods.api.payment.entity.Payments;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final InitService initService;

    @PostConstruct
    public void init() {
//        initService.paymentDummy();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void paymentDummy() {
            for (int i = 1; i <= 200; i++) {
                Member foundMember = em.find(Member.class, (long) i);
                Goods foundGoods = em.find(Goods.class, 4002L);
                Orders orders = Orders.builder()
                        .orderUid("ORDER-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")))
                        .orderStatus(OrderStatus.READY)
                        .orderPrice(foundGoods.getPrice())
                        .createdTime(LocalDateTime.now().minusDays(200).minusDays(i))
                        .member(foundMember)
                        .build();
                em.persist(orders);
                OrderGoods orderGoods = OrderGoods.builder()
                        .goods(foundGoods)
                        .orders(orders)
                        .quantity(1)
                        .totalPrice(foundGoods.getPrice())
                        .createdTime(LocalDateTime.now().minusDays(200).minusDays(i))
                        .goodsDesign("화이트")
                        .build();
                em.persist(orderGoods);

                Payments payments = Payments.builder()
                        .paymentUid("PAYMENT-" + orders.getOrderUid().substring("ORDER-".length()))
                        .amount(orders.getOrderPrice())
                        .createdTime(LocalDateTime.now().minusDays(200).minusDays(i))
                        .orders(orders)
                        .build();
                em.persist(payments);
            }
        }
    }
}
