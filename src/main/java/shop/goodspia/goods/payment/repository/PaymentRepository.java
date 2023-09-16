package shop.goodspia.goods.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.payment.entity.Payments;

public interface PaymentRepository extends JpaRepository<Payments, Long> {
}
