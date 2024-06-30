package shop.goodspia.goods.api.order.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shop.goodspia.goods.api.member.entity.Member;
import shop.goodspia.goods.api.order.dto.OrderStatus;
import shop.goodspia.goods.api.payment.entity.Payments;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Orders {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private Long id;

    @Column(unique = true)
    private String orderUid; //주문 UID

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Integer orderPrice;

    @CreatedDate
    private LocalDateTime createdTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private final List<OrderGoods> orderGoods = new ArrayList<>();

    @OneToOne(mappedBy = "orders")
    private Payments payments;

    //주문 생성
    public static Orders of(Member member, List<OrderGoods> orderGoodsList, int orderPrice) {
        Orders orders = Orders.builder()
                .orderUid("ORDER-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")))
                .orderStatus(OrderStatus.READY)
                .orderPrice(orderPrice)
                .member(member)
                .build();

        for (OrderGoods orderGoods : orderGoodsList) {
            orderGoods.addOrders(orders);
            orders.getOrderGoods().add(orderGoods);
        }

        return orders;
    }

    //주문 상태 변경
    public void completeOrder() {
        this.orderStatus = OrderStatus.COMPLETE;
    }
}
