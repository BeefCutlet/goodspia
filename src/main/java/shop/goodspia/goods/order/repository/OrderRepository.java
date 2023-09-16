package shop.goodspia.goods.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.order.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    Orders findByOrderUid(String orderUid);
}
