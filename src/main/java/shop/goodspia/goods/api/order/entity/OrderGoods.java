package shop.goodspia.goods.api.order.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shop.goodspia.goods.api.goods.entity.Goods;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class OrderGoods {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_goods_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "orders_id")
    private Orders orders;

    private int quantity;
    private int totalPrice;
    private String goodsDesign;
    @CreatedDate
    private LocalDateTime createdTime;

    public static OrderGoods from(Goods goods, int quantity, int totalPrice, String goodsDesign) {
        return OrderGoods.builder()
                .goods(goods)
                .quantity(quantity)
                .totalPrice(totalPrice)
                .goodsDesign(goodsDesign)
                .build();
    }

    public void addOrders(Orders orders) {
        this.orders = orders;
    }
}
