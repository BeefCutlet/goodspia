package shop.goodspia.goods.api.coupon.service.policy;

import shop.goodspia.goods.api.coupon.entity.Coupon;

public interface CouponDiscountPolicy {

    int discount(int originOrderPrice, Coupon coupon);
}
