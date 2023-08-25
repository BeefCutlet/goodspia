package shop.goodspia.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.entity.Payments;

public interface PaymentRepository extends JpaRepository<Payments, Long> {
}
