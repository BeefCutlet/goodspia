package shop.goodspia.goods.api.coupon.service.policy;

import shop.goodspia.goods.api.coupon.entity.Coupon;

public interface CouponPolicy {

    int discount(int originOrderPrice, Coupon coupon);
}
