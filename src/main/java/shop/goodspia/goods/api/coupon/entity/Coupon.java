package shop.goodspia.goods.api.coupon.entity;

import lombok.*;
import shop.goodspia.goods.api.artist.entity.Artist;
import shop.goodspia.goods.api.coupon.dto.CouponSaveRequest;
import shop.goodspia.goods.api.goods.entity.Goods;
import shop.goodspia.goods.global.common.entity.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Coupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;
    private String name;
    private String code;
    @Enumerated(EnumType.STRING)
    private RoundingPolicy roundingPolicy;
    @Enumerated(EnumType.STRING)
    private RoundingPlace roundingPlace;
    @Enumerated(EnumType.STRING)
    private DiscountPolicy discountPolicy;
    private int discountAmount;
    private int discountLimit;
    private int minimumOrderValue;
    private int isExpired;
    private LocalDateTime expiryTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    public static Coupon of(CouponSaveRequest couponSaveRequest, Goods goods) {
        return Coupon.builder()
                .name(couponSaveRequest.getName())
                .code(UUID.randomUUID().toString())
                .roundingPolicy(couponSaveRequest.getRoundingPolicy())
                .roundingPlace(couponSaveRequest.getRoundingPlace())
                .discountPolicy(couponSaveRequest.getDiscountPolicy())
                .discountAmount(couponSaveRequest.getDiscountAmount())
                .discountLimit(couponSaveRequest.getDiscountLimit())
                .minimumOrderValue(couponSaveRequest.getMinimumOrderValue())
                .isExpired(0)
                .expiryTime(couponSaveRequest.getExpiryTime())
                .goods(goods)
                .build();
    }

    public enum DiscountPolicy {
        PERCENTAGE, FIXED_AMOUNT //정률 할인, 정액 할인
    }

    public enum RoundingPolicy {
        UP, DOWN, OFF //올림, 내림, 반올림
    }

    public enum RoundingPlace {
        UNITS, TENS //일의 자리, 십의 자리
    }
}
