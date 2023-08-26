package shop.goodspia.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.entity.OrderGoods;

public interface OrderGoodsRepository extends JpaRepository<OrderGoods, Long> {
}
