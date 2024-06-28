package shop.goodspia.goods.api.coupon.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class MemberCouponId implements Serializable {

    private Long memberId;
    private Long couponId;
}
