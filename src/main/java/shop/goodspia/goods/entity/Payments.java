package shop.goodspia.goods.entity;

import lombok.*;
import shop.goodspia.goods.dto.payment.PaymentRequestDto;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Payments {

    @Id @GeneratedValue
    @Column(name = "payments_id")
    private Long id;
    private String paymentUid; //결제번호
    private int amount; //결제금액
    @Enumerated(EnumType.STRING)
    private AccountBank accountBank;
    private String cardNumber;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @OneToOne(mappedBy = "payments")
    private Orders orders;

    public static Payments createPayments(PaymentRequestDto paymentRequestDto) {
        return Payments.builder()
                .paymentUid(paymentRequestDto.getPaymentUid())
                .amount(paymentRequestDto.getAmount())
                .accountBank(AccountBank.convertStringToBank(paymentRequestDto.getCardBank()))
                .cardNumber(paymentRequestDto.getCardNumber())
                .paymentStatus(PaymentStatus.COMPLETE)
                .build();
    }

    public void addOrder(Orders orders) {
        this.orders = orders;
    }
}
