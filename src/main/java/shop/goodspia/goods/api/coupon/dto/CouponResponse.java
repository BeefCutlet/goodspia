package shop.goodspia.goods.api.coupon.dto;

import lombok.Builder;
import lombok.Getter;
import shop.goodspia.goods.api.coupon.entity.Coupon;

import java.time.LocalDateTime;

@Getter
@Builder
public class CouponResponse {

    private Long couponId;
    private String couponCode;
    private String couponName;
    private Coupon.DiscountPolicy discountPolicy;
    private int discountAmount;
    private int minimumOrderValue;
    private int discountLimit;
    private boolean isExpired;
    private LocalDateTime expiryTime;

    public static CouponResponse from(Coupon coupon) {
        return CouponResponse.builder()
                .couponId(coupon.getId())
                .couponCode(coupon.getCode())
                .couponName(coupon.getName())
                .discountPolicy(coupon.getDiscountPolicy())
                .discountAmount(coupon.getDiscountAmount())
                .minimumOrderValue(coupon.getMinimumOrderValue())
                .discountLimit(coupon.getDiscountLimit())
                .isExpired(coupon.getIsExpired() == 1)
                .expiryTime(coupon.getExpiryTime())
                .build();
    }
}
