package shop.goodspia.goods.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.cart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
