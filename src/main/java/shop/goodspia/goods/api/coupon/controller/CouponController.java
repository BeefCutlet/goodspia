package shop.goodspia.goods.api.coupon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.api.coupon.dto.CouponSaveRequest;
import shop.goodspia.goods.api.coupon.service.CouponService;
import shop.goodspia.goods.global.security.dto.MemberPrincipal;

import javax.validation.Valid;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    /**
     * 쿠폰 등록
     */
    @PostMapping
    public ResponseEntity<Void> registerCoupon(@RequestBody @Valid final CouponSaveRequest request,
                                               @AuthenticationPrincipal final MemberPrincipal principal) {
        Long memberId = principal.getId();
        couponService.registerCoupon(memberId, request);
        return ResponseEntity.noContent().build();
    }

    /**
     * 쿠폰 삭제
     */
    @DeleteMapping("/{couponId}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable final Long couponId) {
        couponService.deleteCoupon(couponId);
        return ResponseEntity.noContent().build();
    }
}
