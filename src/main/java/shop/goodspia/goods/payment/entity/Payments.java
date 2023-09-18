package shop.goodspia.goods.payment.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import shop.goodspia.goods.common.dto.AccountBank;
import shop.goodspia.goods.payment.dto.PaymentStatus;
import shop.goodspia.goods.order.entity.Orders;
import shop.goodspia.goods.payment.dto.PaymentRequest;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Payments {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payments_id")
    private Long id;
    private String paymentUid; //결제번호
    private int amount; //결제금액
    @Enumerated(EnumType.STRING)
    private AccountBank accountBank;
    private String cardNumber;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @CreatedDate
    private LocalDateTime createdTime;

    @OneToOne(mappedBy = "payments")
    private Orders orders;

    public static Payments createPayments(PaymentRequest paymentRequest) {
        return Payments.builder()
                .paymentUid(paymentRequest.getPaymentUid())
                .amount(paymentRequest.getAmount())
                .accountBank(AccountBank.convertStringToBank(paymentRequest.getCardBank()))
                .cardNumber(paymentRequest.getCardNumber())
                .paymentStatus(PaymentStatus.COMPLETE)
                .build();
    }

    public void addOrder(Orders orders) {
        this.orders = orders;
    }
}
