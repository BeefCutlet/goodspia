package shop.goodspia.goods.payment.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.payment.entity.Payments;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payments, Long> {

    @EntityGraph(attributePaths = "orders")
    Optional<Payments> findPaymentsById(Long id);
}
