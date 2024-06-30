package shop.goodspia.goods.api.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.api.coupon.entity.MemberCoupon;
import shop.goodspia.goods.api.coupon.entity.MemberCouponId;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, MemberCouponId> {
}
