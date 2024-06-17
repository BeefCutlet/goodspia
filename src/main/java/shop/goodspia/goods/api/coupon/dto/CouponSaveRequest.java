package shop.goodspia.goods.api.coupon.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.goodspia.goods.api.coupon.entity.Coupon;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponSaveRequest {

    private Long goodsId;
    private String name;
    private Coupon.RoundingPolicy roundingPolicy;
    private Coupon.RoundingPlace roundingPlace;
    private Coupon.DiscountPolicy discountPolicy;
    private int discountAmount;
    private int discountLimit;
    private int minimumOrderValue;
    private LocalDateTime expiryTime;
}
