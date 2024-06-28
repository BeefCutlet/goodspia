package shop.goodspia.goods.api.coupon.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import shop.goodspia.goods.api.coupon.entity.Coupon;
import shop.goodspia.goods.api.coupon.entity.MemberCoupon;

import javax.persistence.EntityManager;
import java.util.List;

import static shop.goodspia.goods.api.coupon.entity.QCoupon.coupon;
import static shop.goodspia.goods.api.coupon.entity.QMemberCoupon.memberCoupon;

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
        return queryFactory
                .select(coupon)
                .from(coupon)
                .where(coupon.artist.id.eq(artistId))
                .fetch();
    }

    /**
     * 아티스트가 등록한 쿠폰 단건 조회
     */
    public Coupon findRegisteredCoupon(Long artistId, Long couponId) {
        return queryFactory
                .select(coupon)
                .from(coupon)
                .where(coupon.id.eq(couponId).and(coupon.artist.id.eq(artistId)))
                .fetchOne();
    }

    /**
     * 회원이 받은 쿠폰 목록 조회
     */
    public List<Coupon> findReceivedCouponList(Long memberId) {
        return queryFactory
                .select(coupon)
                .from(memberCoupon)
                .join(memberCoupon.coupon, coupon)
                .where(memberCoupon.memberId.eq(memberId))
                .fetch();
    }

    /**
     * 회원이 받은 쿠폰 단건 조회
     */
    public Coupon findReceivedCoupon(Long memberId, Long couponId) {
        return queryFactory
                .select(coupon)
                .from(memberCoupon)
                .join(memberCoupon.coupon, coupon)
                .where(memberCoupon.memberId.eq(memberId).and(memberCoupon.couponId.eq(couponId)))
                .fetchOne();
    }

    /**
     * 특정 굿즈에 대한 쿠폰 목록 조회
     */
    public List<Coupon> findGoodsCoupons(Long goodsId) {
        return queryFactory
                .select(coupon)
                .from(coupon)
                .where(coupon.goods.id.eq(goodsId))
                .fetch();
    }

    /**
     * 특정 굿즈에 대한 쿠폰 목록 조회 (회원의 수령 여부 포함)
     */
//    public List<Coupon> findCouponListMemberReceived(Long memberId, List<Coupon> coupons) {
//        return queryFactory
//                .select(memberCoupon)
//                .from(memberCoupon)
//                .where(memberIdAndCouponIdClause(memberId, coupons))
//                .fetch();
//    }

    private BooleanBuilder memberIdAndCouponIdClause(Long memberId, List<Coupon> coupons) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (Coupon clauseCoupon : coupons) {
            booleanBuilder.or(memberCoupon.memberId.eq(memberId).and(memberCoupon.couponId.eq(clauseCoupon.getId())));
        }
        return booleanBuilder;
    }

    /**
     * 특정 회원이 가진 특정 쿠폰 조회
     */
    public MemberCoupon findMemberCoupon(Long memberId, Long couponId) {
        return queryFactory.select(memberCoupon)
                .from(memberCoupon)
                .where(memberCoupon.memberId.eq(memberId).and(memberCoupon.couponId.eq(couponId)))
                .fetchOne();
    }
}
