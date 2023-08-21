package shop.goodspia.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
