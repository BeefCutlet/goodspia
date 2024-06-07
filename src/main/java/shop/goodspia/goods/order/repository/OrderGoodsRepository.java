package shop.goodspia.goods.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.order.entity.OrderGoods;
import shop.goodspia.goods.order.entity.Orders;

public interface OrderGoodsRepository extends JpaRepository<OrderGoods, Long> {

    void deleteAllByOrders(Orders orders);
}
