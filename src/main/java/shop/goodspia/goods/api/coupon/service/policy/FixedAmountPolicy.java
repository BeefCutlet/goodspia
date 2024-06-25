package shop.goodspia.goods.api.coupon.service.policy;

import org.springframework.stereotype.Component;
import shop.goodspia.goods.api.coupon.entity.Coupon;

@Component
public class FixedAmountPolicy implements CouponPolicy {
    @Override
    public int discount(final int originOrderPrice, final Coupon coupon) {
        int discountOrderPrice = originOrderPrice;
        discountOrderPrice -= coupon.getDiscountAmount();

        //할인된 금액이 0 미만이면 0으로 수정
        if (discountOrderPrice < 0) {
            discountOrderPrice = 0;
        }

        //할인된 금액이 최대 할인 금액 미만이면 최대 할인 금액으로 수정
        if (coupon.getDiscountLimit() > 0 && discountOrderPrice < coupon.getDiscountLimit()) {
            discountOrderPrice = coupon.getDiscountLimit();
        }

        return discountOrderPrice;
    }
}
