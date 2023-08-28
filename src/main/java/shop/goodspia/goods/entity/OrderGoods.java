package shop.goodspia.goods.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderGoods extends BaseTimeEntity {

    @Id @GeneratedValue
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

    public static OrderGoods createOrderGoods(Goods goods, int quantity, int totalPrice, String goodsDesign) {
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
