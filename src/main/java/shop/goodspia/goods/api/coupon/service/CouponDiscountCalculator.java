package shop.goodspia.goods.api.coupon.service;

import org.springframework.stereotype.Component;
import shop.goodspia.goods.api.coupon.entity.Coupon;
import shop.goodspia.goods.api.coupon.service.policy.CouponDiscountPolicy;
import shop.goodspia.goods.api.coupon.service.policy.FixedAmountPolicy;
import shop.goodspia.goods.api.coupon.service.policy.PercentagePolicy;

@Component
public class CouponDiscountCalculator {

    public int discount(int originOrderPrice, Coupon coupon) {
        validateCoupon(originOrderPrice, coupon);

        CouponDiscountPolicy couponDiscountPolicy = selectDiscountPolicy(coupon.getDiscountPolicy());
        return couponDiscountPolicy.discount(originOrderPrice, coupon);
    }

    private CouponDiscountPolicy selectDiscountPolicy(Coupon.DiscountPolicy discountPolicy) {
        CouponDiscountPolicy couponDiscountPolicy = null;
        switch (discountPolicy) {
            case PERCENTAGE:
                couponDiscountPolicy = new PercentagePolicy();
                break;
            case FIXED_AMOUNT:
                couponDiscountPolicy = new FixedAmountPolicy();
        }

        if (couponDiscountPolicy == null) {
            throw new IllegalArgumentException("쿠폰 정책이 선택되지 않았습니다.");
        }

        return couponDiscountPolicy;
    }

    private void validateCoupon(int originOrderPrice, Coupon coupon) {
        //주문 금액이 최소 주문 금액보다 높은지 확인
        if (originOrderPrice < coupon.getMinimumOrderValue()) {
            throw new IllegalArgumentException("최소 주문 금액을 만족하지 못했습니다.");
        }

        //쿠폰 정책에 따른 할인 단위에 적합한지 확인
        //정액 할인 - 1000~
        //정률 할인 - 0~100
        switch (coupon.getDiscountPolicy()) {
            case PERCENTAGE:
                if (coupon.getDiscountAmount() < 0 || coupon.getDiscountAmount() > 100) {
                    throw new IllegalArgumentException("정률 할인의 할인 단위는 0~100 사이여야 합니다.");
                }
                break;
            case FIXED_AMOUNT:
                if (coupon.getDiscountAmount() < 1000) {
                    throw new IllegalArgumentException("정액 할인의 할인 단위는 1000 이상이어야 합니다.");
                }
        }
    }
}
