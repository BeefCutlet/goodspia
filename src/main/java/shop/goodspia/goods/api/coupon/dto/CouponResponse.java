package shop.goodspia.goods.api.coupon.dto;

import lombok.Builder;
import lombok.Getter;
import shop.goodspia.goods.api.coupon.entity.Coupon;

import java.time.LocalDateTime;

@Getter
@Builder
public class CouponResponse {

    private String couponName;
    private int discountAmount;
    private int minimumOrderValue;
    private int discountLimit;
    private LocalDateTime expiryTime;

    public static CouponResponse from(Coupon coupon) {
        return CouponResponse.builder()
                .couponName(coupon.getName())
                .discountAmount(coupon.getDiscountAmount())
                .minimumOrderValue(coupon.getMinimumOrderValue())
                .discountLimit(coupon.getDiscountLimit())
                .expiryTime(coupon.getExpiryTime())
                .build();
    }
}
