package shop.goodspia.goods.api.coupon.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import shop.goodspia.goods.api.coupon.entity.Coupon;
import shop.goodspia.goods.api.coupon.entity.QMemberCoupon;

import javax.persistence.EntityManager;
import java.util.List;

import static shop.goodspia.goods.api.coupon.entity.QCoupon.coupon;
import static shop.goodspia.goods.api.coupon.entity.QMemberCoupon.*;

@Repository
public class CouponQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CouponQueryRepository(final EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 아티스트가 등록한 쿠폰 목록 조회
     */
    public List<Coupon> findRegisteredCouponList(Long artistId) {
        return queryFactory.select(coupon)
                .from(coupon)
                .where(coupon.artist.id.eq(artistId))
                .fetch();
    }

    /**
     * 아티스트가 등록한 쿠폰 단건 조회
     */
    public Coupon findRegisteredCoupon(Long artistId, Long couponId) {
        return queryFactory.select(coupon)
                .from(coupon)
                .where(coupon.id.eq(couponId).and(coupon.artist.id.eq(artistId)))
                .fetchOne();
    }

    /**
     * 회원이 받은 쿠폰 목록 조회
     */
    public List<Coupon> findReceivedCouponList(Long memberId) {
        return queryFactory.select(coupon)
                .from(memberCoupon)
                .join(memberCoupon.coupon, coupon)
                .where(memberCoupon.id.memberId.eq(memberId))
                .fetch();
    }

    /**
     * 회원이 받은 쿠폰 단건 조회
     */
    public Coupon findReceivedCoupon(Long memberId, Long couponId) {
        return queryFactory.select(coupon)
                .from(memberCoupon)
                .join(memberCoupon.coupon, coupon)
                .where(memberCoupon.id.memberId.eq(memberId).and(memberCoupon.id.couponId.eq(couponId)))
                .fetchOne();
    }
}
