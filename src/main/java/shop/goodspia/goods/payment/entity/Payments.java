package shop.goodspia.goods.payment.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shop.goodspia.goods.common.dto.AccountBank;
import shop.goodspia.goods.payment.dto.PaymentStatus;
import shop.goodspia.goods.order.entity.Orders;
import shop.goodspia.goods.payment.dto.PaymentRequest;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Payments {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payments_id")
    private Long id;
    private String paymentUid; //결제번호
    private int amount; //결제금액
    @CreatedDate
    private LocalDateTime createdTime;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "orders_id")
    private Orders orders;

    public static Payments from(PaymentRequest paymentRequest, Orders order) {
        return Payments.builder()
                .paymentUid(paymentRequest.getPaymentUid())
                .amount(paymentRequest.getAmount())
                .orders(order)
                .build();
    }

    public void addOrder(Orders orders) {
        this.orders = orders;
    }
}
