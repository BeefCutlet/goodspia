package shop.goodspia.goods.api.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.api.order.entity.OrderGoods;
import shop.goodspia.goods.api.order.entity.Orders;

public interface OrderGoodsRepository extends JpaRepository<OrderGoods, Long> {

    void deleteAllByOrders(Orders orders);
}
