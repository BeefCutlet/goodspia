package shop.goodspia.goods.api.coupon.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MemberCouponId implements Serializable {

    private Long memberId;
    private Long couponId;
}
