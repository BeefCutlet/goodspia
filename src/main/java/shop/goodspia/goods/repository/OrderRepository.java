package shop.goodspia.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    Orders findByOrderUid(String orderUid);
}
