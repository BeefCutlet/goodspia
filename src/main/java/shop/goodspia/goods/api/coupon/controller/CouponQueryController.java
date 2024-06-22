package shop.goodspia.goods.api.coupon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.api.coupon.dto.CouponListResponse;
import shop.goodspia.goods.api.coupon.dto.CouponResponse;
import shop.goodspia.goods.api.coupon.service.CouponService;
import shop.goodspia.goods.global.security.dto.MemberPrincipal;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponQueryController {

    private final CouponService couponService;

    /**
     * 회원이 받은 쿠폰 목록 조회
     */
    @GetMapping
    public ResponseEntity<CouponListResponse> getReceivedCouponList(@AuthenticationPrincipal final MemberPrincipal principal) {
        Long memberId = principal.getId();
        CouponListResponse receivedCouponList = couponService.getReceivedCouponList(memberId);
        return ResponseEntity.ok(receivedCouponList);
    }

    /**
     * 회원이 받은 쿠폰 단건 조회
     */
    @GetMapping("/{couponId}")
    public ResponseEntity<CouponResponse> getReceivedCoupon(@AuthenticationPrincipal MemberPrincipal principal,
                                                            @PathVariable final Long couponId) {
        Long memberId = principal.getId();
        couponService.getReceivedCoupon(memberId, couponId);
        return ResponseEntity.ok().build();
    }

    /**
     * 아티스트가 등록한 쿠폰 목록 조회
     */
    @GetMapping("/artist")
    public ResponseEntity<CouponListResponse> getRegisteredCouponList(@AuthenticationPrincipal final MemberPrincipal principal) {
        Long memberId = principal.getId();
        CouponListResponse registeredCouponList = couponService.getRegisteredCouponList(memberId);
        return ResponseEntity.ok(registeredCouponList);
    }

    /**
     * 아티스트가 등록한 쿠폰 단건 조회
     */
    @GetMapping("/artist/{couponId}")
    public ResponseEntity<CouponResponse> getRegisteredCoupon(@AuthenticationPrincipal final MemberPrincipal principal,
                                                              @PathVariable final Long couponId) {
        Long memberId = principal.getId();
        CouponResponse registeredCoupon = couponService.getRegisteredCoupon(memberId, couponId);
        return ResponseEntity.ok(registeredCoupon);
    }
}
