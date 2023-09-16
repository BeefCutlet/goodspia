package shop.goodspia.goods.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.order.entity.OrderGoods;

public interface OrderGoodsRepository extends JpaRepository<OrderGoods, Long> {
}
