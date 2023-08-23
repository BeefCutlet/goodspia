package shop.goodspia.goods.entity;

import lombok.*;
import shop.goodspia.goods.dto.payment.PaymentRequestDto;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Payment extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "payment_id")
    private Long id;
    private int quantity;
    private int totalPrice;
    @Enumerated(EnumType.STRING)
    private AccountBank accountBank;
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @ManyToOne(fetch = LAZY)
    private Goods goods;
    @ManyToOne(fetch = LAZY)
    private Member member;
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    public static Payment createPayment(PaymentRequestDto paymentRequestDto) {
        return Payment.builder()
                .quantity(paymentRequestDto.getQuantity())
                .totalPrice(paymentRequestDto.getTotalPrice())
                .accountBank(AccountBank.covertToBank(paymentRequestDto.getAccountBank()))
                .accountNumber(paymentRequestDto.getAccountNumber())
                .paymentStatus(PaymentStatus.READY)
                .goods(paymentRequestDto.getGoods())
                .member(paymentRequestDto.getMember())
                .build();
    }

    public void addDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void changePaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
