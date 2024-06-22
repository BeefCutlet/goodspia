package shop.goodspia.goods.api.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.api.coupon.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
