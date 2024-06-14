package shop.goodspia.goods.api.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.api.order.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    Orders findByOrderUid(String orderUid);
}
