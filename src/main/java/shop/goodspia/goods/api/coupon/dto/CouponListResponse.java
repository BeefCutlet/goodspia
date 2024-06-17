package shop.goodspia.goods.api.coupon.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.goodspia.goods.api.coupon.entity.Coupon;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponListResponse {

    private List<CouponResponse> coupons;

    public static CouponListResponse from(List<Coupon> foundCoupons) {
        List<CouponResponse> couponList = foundCoupons.stream().map(CouponResponse::from).collect(Collectors.toList());
        return new CouponListResponse(couponList);
    }
}
