package shop.goodspia.goods.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Orders extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "orders_id")
    private Long id;

    @Column(unique = true)
    private String orderUid; //주문 UID

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private final List<OrderGoods> orderGoods = new ArrayList<>();

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "payments_id")
    private Payments payments;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //주문 생성
    public static Orders createOrder(Member member, List<OrderGoods> orderGoodsList) {
        Orders orders = Orders.builder()
                .orderUid("ORDER_" + System.currentTimeMillis())
                .orderStatus(OrderStatus.READY)
                .member(member)
                .build();

        for (OrderGoods orderGoods : orderGoodsList) {
            orderGoods.addOrders(orders);
            orders.getOrderGoods().add(orderGoods);
        }

        return orders;
    }

    //주문 취소
    public void cancel() {
        if (delivery.getDeliveryStatus() == DeliveryStatus.DELIVERY_COMPLETE) {
            throw new IllegalStateException("이미 배송된 상품은 취소할 수 없습니다.");
        }
        this.orderStatus = OrderStatus.CANCEL;
    }

    //결제 생성 후 연결
    public void addPayments(Payments payments) {
        this.payments = payments;
        payments.addOrder(this);
    }

    //배달 생성 후 연결
    public void addDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.addDelivery(this);
    }
}
