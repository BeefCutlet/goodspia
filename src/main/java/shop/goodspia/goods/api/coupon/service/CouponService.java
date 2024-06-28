package shop.goodspia.goods.api.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.api.artist.entity.Artist;
import shop.goodspia.goods.api.artist.repository.ArtistRepository;
import shop.goodspia.goods.api.coupon.dto.CouponListResponse;
import shop.goodspia.goods.api.coupon.dto.CouponReceiveSaveRequest;
import shop.goodspia.goods.api.coupon.dto.CouponResponse;
import shop.goodspia.goods.api.coupon.dto.CouponSaveRequest;
import shop.goodspia.goods.api.coupon.entity.Coupon;
import shop.goodspia.goods.api.coupon.entity.MemberCoupon;
import shop.goodspia.goods.api.coupon.repository.CouponQueryRepository;
import shop.goodspia.goods.api.coupon.repository.CouponRepository;
import shop.goodspia.goods.api.coupon.repository.MemberCouponRepository;
import shop.goodspia.goods.api.goods.entity.Goods;
import shop.goodspia.goods.api.goods.repository.GoodsQueryRepository;
import shop.goodspia.goods.api.member.entity.Member;
import shop.goodspia.goods.api.member.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {

    private final MemberRepository memberRepository;
    private final ArtistRepository artistRepository;
    private final CouponRepository couponRepository;
    private final CouponQueryRepository couponQueryRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final GoodsQueryRepository goodsQueryRepository;

    /**
     * 쿠폰 등록
     */
    public void registerCoupon(final Long memberId, final CouponSaveRequest couponSaveRequest) {
        //회원 정보 조회
        Artist foundArtist = getArtist(memberId);

        //제품 정보 조회
        Goods foundGoods = getGoods(couponSaveRequest);

        if (!foundArtist.getId().equals(foundGoods.getArtist().getId())) {
            throw new IllegalArgumentException("쿠폰 등록 권한이 없습니다.");
        }

        Coupon coupon = Coupon.of(couponSaveRequest, foundGoods, foundArtist);
        couponRepository.save(coupon);
    }

    /**
     * 쿠폰 수령
     */
    public void receiveCoupon(Long memberId, CouponReceiveSaveRequest couponReceiveSaveRequest) {
        validateMemberCouponExist(memberId, couponReceiveSaveRequest.getCouponId());
        Member foundMember = getMember(memberId);
        Coupon foundCoupon = getCoupon(couponReceiveSaveRequest.getCouponId());

        MemberCoupon memberCoupon = MemberCoupon.of(foundMember, foundCoupon);
        memberCouponRepository.save(memberCoupon);
    }

    /**
     * 쿠폰 삭제
     */
    public void deleteCoupon(final Long couponId) {
        Coupon foundCoupon = getCoupon(couponId);
        couponRepository.delete(foundCoupon);
    }

    /**
     * 회원이 받은 쿠폰 목록 조회
     */
    @Transactional(readOnly = true)
    public CouponListResponse getReceivedCouponList(final Long memberId) {
        List<Coupon> registeredCouponList = couponQueryRepository.findReceivedCouponList(memberId);
        return CouponListResponse.from(registeredCouponList);
    }

    /**
     * 회원이 받은 쿠폰 단건 조회
     */
    @Transactional(readOnly = true)
    public CouponResponse getReceivedCoupon(final Long memberId, final Long couponId) {
        Coupon receivedCoupon = couponQueryRepository.findReceivedCoupon(memberId, couponId);
        return CouponResponse.from(receivedCoupon);
    }

    /**
     * 특정 굿즈에 대한 쿠폰 목록 조회
     */
    @Transactional(readOnly = true)
    public CouponListResponse getGoodsCoupons(Long memberId, Long goodsId) {
        List<Coupon> foundCoupons = couponQueryRepository.findGoodsCoupons(goodsId);
        //로그인을 안 했으면 쿠폰 정보만 반환
        if (memberId == null) {
            return CouponListResponse.from(foundCoupons);
        }

        //로그인을 했으면 쿠폰 정보에 사용 여부 검증
//        List<Coupon> foundUsedCoupons = couponQueryRepository.findCouponListMemberReceived(memberId, foundCoupons);
        return CouponListResponse.from(foundCoupons);
    }

    /**
     * 아티스트가 등록한 쿠폰 목록 조회
     */
    @Transactional(readOnly = true)
    public CouponListResponse getRegisteredCouponList(final Long memberId) {
        Artist foundArtist = getArtist(memberId);
        List<Coupon> registeredCouponList = couponQueryRepository.findRegisteredCouponList(foundArtist.getId());
        return CouponListResponse.from(registeredCouponList);
    }

    /**
     * 아티스트가 등록한 쿠폰 단건 조회
     */
    @Transactional(readOnly = true)
    public CouponResponse getRegisteredCoupon(Long memberId, Long couponId) {
        Artist foundArtist = getArtist(memberId);
        Coupon registeredCoupon = couponQueryRepository.findRegisteredCoupon(foundArtist.getId(), couponId);
        return CouponResponse.from(registeredCoupon);
    }

    private Coupon getCoupon(final Long couponId) {
        return couponRepository.findById(couponId).orElseThrow(() -> {
            throw new IllegalArgumentException("쿠폰 정보가 존재하지 않습니다.");
        });
    }

    private Artist getArtist(final Long memberId) {
        return artistRepository.findArtistByMemberId(memberId).orElseThrow(() -> {
            throw new IllegalArgumentException("아티스트 정보가 존재하지 않습니다.");
        });
    }

    private Goods getGoods(final CouponSaveRequest couponSaveRequest) {
        return Optional.of(goodsQueryRepository.findGoodsDetail(couponSaveRequest.getGoodsId())).orElseThrow(() -> {
            throw new IllegalArgumentException("판매 진행 중인 굿즈 정보가 존재하지 않습니다.");
        });
    }

    private Member getMember(final Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> {
            throw new IllegalArgumentException("회원 정보가 존재하지 않습니다.");
        });
    }

    private void validateMemberCouponExist(final Long memberId, final Long couponId) {
        MemberCoupon foundMemberCoupon = couponQueryRepository.findMemberCoupon(memberId, couponId);
        if (foundMemberCoupon != null) {
            throw new IllegalArgumentException("이미 수령한 쿠폰입니다.");
        }
    }
}
