package shop.goodspia.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
