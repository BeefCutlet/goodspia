package shop.goodspia.goods.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
public class Payment extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "payment_id")
    private Long id;
    private int quantity;
    private int totalPrice;
    @Enumerated(EnumType.STRING)
    private AccountBank accountBank;
    private String accountNumber;

    @ManyToOne(fetch = LAZY)
    private Goods goods;
    @ManyToOne(fetch = LAZY)
    private Member member;
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
}
