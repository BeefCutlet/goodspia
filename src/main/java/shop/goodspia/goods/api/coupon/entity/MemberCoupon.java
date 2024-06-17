package shop.goodspia.goods.api.coupon.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import shop.goodspia.goods.api.member.entity.Member;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberCoupon {

    @EmbeddedId
    private MemberCouponId id;

    @CreatedDate
    private LocalDateTime createdDate;

    @MapsId("memberId")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @MapsId("couponId")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
}
