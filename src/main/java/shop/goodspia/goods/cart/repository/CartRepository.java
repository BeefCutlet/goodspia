package shop.goodspia.goods.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.goodspia.goods.cart.entity.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c where c.member.id = :memberId and c.goods.id = :goodsId and c.design.id = :designId")
    Optional<Cart> findAddedCart(Long memberId, Long goodsId, Long designId);
}
