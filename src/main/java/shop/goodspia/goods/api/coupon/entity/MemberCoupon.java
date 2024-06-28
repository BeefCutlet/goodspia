package shop.goodspia.goods.api.coupon.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shop.goodspia.goods.api.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@IdClass(MemberCouponId.class)
@EntityListeners(AuditingEntityListener.class)
public class MemberCoupon {

    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Id
    @Column(name = "coupon_id")
    private Long couponId;

    private int isEnabled;

    @CreatedDate
    private LocalDateTime createdTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "coupon_id", insertable = false, updatable = false)
    private Coupon coupon;

    public static MemberCoupon of(Member member, Coupon coupon) {
        return MemberCoupon.builder()
                .memberId(member.getId())
                .couponId(coupon.getId())
                .isEnabled(1)
                .build();
    }
}
