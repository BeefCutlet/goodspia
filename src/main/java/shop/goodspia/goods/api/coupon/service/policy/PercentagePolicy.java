package shop.goodspia.goods.api.coupon.service.policy;

import org.springframework.stereotype.Component;
import shop.goodspia.goods.api.coupon.entity.Coupon;

@Component
public class PercentagePolicy implements CouponPolicy {
    @Override
    public int discount(final int originOrderPrice, final Coupon coupon) {
        int discountOrderPrice = originOrderPrice;
        double tempPrice = (double) discountOrderPrice * (100 - coupon.getDiscountAmount()) / 100;

        //자릿수에 따라 올림, 내림, 반올림
        discountOrderPrice = rounding(tempPrice, coupon.getRoundingPolicy(), coupon.getRoundingPlace());

        //할인된 금액이 최대 할인 금액 미민이면 최대 할인 금액으로 변경
        if (discountOrderPrice > 0 && discountOrderPrice < coupon.getDiscountLimit()) {
            discountOrderPrice = coupon.getDiscountLimit();
        }

        return discountOrderPrice;
    }

    private int rounding(double orderPrice, Coupon.RoundingPolicy roundingPolicy, Coupon.RoundingPlace roundingPlace) {
        double divDigit = Math.pow(10.0, roundingPlace.getDigit());
        switch (roundingPolicy) {
            case UP:
                orderPrice = Math.ceil((orderPrice / divDigit)) * divDigit;
                break;
            case DOWN:
                orderPrice = Math.floor((orderPrice / divDigit)) * divDigit;
                break;
            case OFF:
                orderPrice = Math.round((orderPrice / divDigit)) * divDigit;
                break;
        }

        return (int) orderPrice;
    }
}
