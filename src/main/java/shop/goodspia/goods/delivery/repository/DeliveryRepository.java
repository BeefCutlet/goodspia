package shop.goodspia.goods.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.delivery.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
