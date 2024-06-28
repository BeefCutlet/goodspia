package shop.goodspia.goods.api.coupon.dto;

import lombok.Getter;

import javax.validation.constraints.Min;

@Getter
public class CouponReceiveSaveRequest {

    @Min(1)
    private Long couponId;
}
