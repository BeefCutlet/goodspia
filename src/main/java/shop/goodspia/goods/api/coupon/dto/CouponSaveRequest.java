package shop.goodspia.goods.api.coupon.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import shop.goodspia.goods.api.coupon.entity.Coupon;
import shop.goodspia.goods.global.common.validator.ValidEnum;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CouponSaveRequest {

    @Min(1)
    private Long goodsId;
    @NotEmpty
    private String name;
    @ValidEnum(enumClass = Coupon.RoundingPolicy.class)
    private Coupon.RoundingPolicy roundingPolicy;
    @ValidEnum(enumClass = Coupon.RoundingPlace.class)
    private Coupon.RoundingPlace roundingPlace;
    @ValidEnum(enumClass = Coupon.DiscountPolicy.class)
    private Coupon.DiscountPolicy discountPolicy;
    @Min(0)
    private int discountAmount;
    @Min(0)
    private int discountLimit;
    @Min(0)
    private int minimumOrderValue;
    private LocalDateTime expiryTime;
}
