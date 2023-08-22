package shop.goodspia.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
