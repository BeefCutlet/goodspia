package shop.goodspia.goods.entity;

import lombok.*;
import shop.goodspia.goods.dto.PaymentDto;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    public static Payment createPayment(PaymentDto paymentDto) {
        return Payment.builder()
                .quantity(paymentDto.getQuantity())
                .totalPrice(paymentDto.getTotalPrice())
                .accountBank(AccountBank.covertToBank(paymentDto.getAccountBank()))
                .accountNumber(paymentDto.getAccountNumber())
                .paymentStatus(PaymentStatus.READY)
                .goods(paymentDto.getGoods())
                .member(paymentDto.getMember())
                .delivery(paymentDto.getDelivery())
                .build();
    }
}
