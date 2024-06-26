package shop.goodspia.goods.unit.coupon;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.goodspia.goods.api.coupon.entity.Coupon;
import shop.goodspia.goods.api.coupon.service.CouponDiscountCalculator;
import shop.goodspia.goods.api.goods.entity.Goods;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class CouponDiscountCalculatorTest {

    @InjectMocks
    private CouponDiscountCalculator couponDiscountCalculator;

    @DisplayName("성공-정률할인")
    @Test
    void percentageSuccess() {
        int originOrderPrice = 10000;
        Coupon coupon = Coupon.builder()
                .name("CouponName")
                .roundingPolicy(Coupon.RoundingPolicy.OFF)
                .roundingPlace(Coupon.RoundingPlace.TENS)
                .discountPolicy(Coupon.DiscountPolicy.PERCENTAGE)
                .discountAmount(20)
                .discountLimit(8000)
                .minimumOrderValue(0)
                .isExpired(0)
                .build();

        int result = couponDiscountCalculator.discount(originOrderPrice, coupon);
        assertThat(result).isEqualTo(8000);
    }

    @DisplayName("성공-정률할인-최대할인금액")
    @Test
    void percentageDiscountLimitSuccess() {
        int originOrderPrice = 10000;
        Coupon coupon = Coupon.builder()
                .name("CouponName")
                .roundingPolicy(Coupon.RoundingPolicy.OFF)
                .roundingPlace(Coupon.RoundingPlace.TENS)
                .discountPolicy(Coupon.DiscountPolicy.PERCENTAGE)
                .discountAmount(20)
                .discountLimit(9000)
                .minimumOrderValue(0)
                .isExpired(0)
                .build();

        int result = couponDiscountCalculator.discount(originOrderPrice, coupon);
        assertThat(result).isEqualTo(9000);
    }

    @DisplayName("성공-정률할인-10의 자리 올림")
    @Test
    void percentageTensPlaceRoundingPolicyUpSuccess() {
        int originOrderPrice = 11000;
        Coupon coupon = Coupon.builder()
                .name("CouponName")
                .roundingPolicy(Coupon.RoundingPolicy.UP)
                .roundingPlace(Coupon.RoundingPlace.TENS)
                .discountPolicy(Coupon.DiscountPolicy.PERCENTAGE)
                .discountAmount(33)
                .discountLimit(2000)
                .minimumOrderValue(0)
                .isExpired(0)
                .build();

        int result = couponDiscountCalculator.discount(originOrderPrice, coupon);
        assertThat(result).isEqualTo(7400);
    }

    @DisplayName("성공-정률할인-10의 자리 내림")
    @Test
    void percentageTensPlaceRoundingPolicyDownSuccess() {
        int originOrderPrice = 11000;
        Coupon coupon = Coupon.builder()
                .name("CouponName")
                .roundingPolicy(Coupon.RoundingPolicy.DOWN)
                .roundingPlace(Coupon.RoundingPlace.TENS)
                .discountPolicy(Coupon.DiscountPolicy.PERCENTAGE)
                .discountAmount(33)
                .discountLimit(2000)
                .minimumOrderValue(0)
                .isExpired(0)
                .build();

        int result = couponDiscountCalculator.discount(originOrderPrice, coupon);
        assertThat(result).isEqualTo(7300);
    }

    @DisplayName("성공-정률할인-10의 자리 반올림")
    @Test
    void percentageTensPlaceRoundingPolicyOffSuccess() {
        int originOrderPrice = 11000;
        Coupon coupon = Coupon.builder()
                .name("CouponName")
                .roundingPolicy(Coupon.RoundingPolicy.OFF)
                .roundingPlace(Coupon.RoundingPlace.TENS)
                .discountPolicy(Coupon.DiscountPolicy.PERCENTAGE)
                .discountAmount(33)
                .discountLimit(2000)
                .minimumOrderValue(0)
                .isExpired(0)
                .build();

        int result = couponDiscountCalculator.discount(originOrderPrice, coupon);
        assertThat(result).isEqualTo(7400);
    }

    @DisplayName("성공-정률할인-1의 자리 올림")
    @Test
    void percentageUnitsPlaceRoundingPolicyUpSuccess() {
        int originOrderPrice = 9900;
        Coupon coupon = Coupon.builder()
                .name("CouponName")
                .roundingPolicy(Coupon.RoundingPolicy.UP)
                .roundingPlace(Coupon.RoundingPlace.UNITS)
                .discountPolicy(Coupon.DiscountPolicy.PERCENTAGE)
                .discountAmount(33)
                .discountLimit(2000)
                .minimumOrderValue(0)
                .isExpired(0)
                .build();

        int result = couponDiscountCalculator.discount(originOrderPrice, coupon);
        assertThat(result).isEqualTo(6640);
    }

    @DisplayName("성공-정률할인-1의 자리 내림")
    @Test
    void percentageUnitsPlaceRoundingPolicyDownSuccess() {
        int originOrderPrice = 9900;
        Coupon coupon = Coupon.builder()
                .name("CouponName")
                .roundingPolicy(Coupon.RoundingPolicy.DOWN)
                .roundingPlace(Coupon.RoundingPlace.UNITS)
                .discountPolicy(Coupon.DiscountPolicy.PERCENTAGE)
                .discountAmount(33)
                .discountLimit(2000)
                .minimumOrderValue(0)
                .isExpired(0)
                .build();

        int result = couponDiscountCalculator.discount(originOrderPrice, coupon);
        assertThat(result).isEqualTo(6630);
    }

    @DisplayName("성공-정률할인-1의 자리 반올림")
    @Test
    void percentageUnitsPlaceRoundingPolicyOffSuccess() {
        int originOrderPrice = 9900;
        Coupon coupon = Coupon.builder()
                .name("CouponName")
                .roundingPolicy(Coupon.RoundingPolicy.OFF)
                .roundingPlace(Coupon.RoundingPlace.UNITS)
                .discountPolicy(Coupon.DiscountPolicy.PERCENTAGE)
                .discountAmount(33)
                .discountLimit(2000)
                .minimumOrderValue(0)
                .isExpired(0)
                .build();

        int result = couponDiscountCalculator.discount(originOrderPrice, coupon);
        assertThat(result).isEqualTo(6630);
    }

    @DisplayName("실패-정률할인 할인 단위 부적합")
    @Test
    void percentageAmountFormatFailed() {
        int originOrderPrice = 10000;
        Coupon coupon = Coupon.builder()
                .name("CouponName")
                .roundingPolicy(Coupon.RoundingPolicy.OFF)
                .roundingPlace(Coupon.RoundingPlace.TENS)
                .discountPolicy(Coupon.DiscountPolicy.PERCENTAGE)
                .discountAmount(1000)
                .discountLimit(9000)
                .minimumOrderValue(11000)
                .isExpired(0)
                .build();

        assertThatThrownBy(() -> couponDiscountCalculator.discount(originOrderPrice, coupon))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("성공-정액할인")
    @Test
    void fixedAmountSuccess() {
        int originOrderPrice = 10000;
        Coupon coupon = Coupon.builder()
                .name("CouponName")
                .roundingPolicy(Coupon.RoundingPolicy.OFF)
                .roundingPlace(Coupon.RoundingPlace.TENS)
                .discountPolicy(Coupon.DiscountPolicy.FIXED_AMOUNT)
                .discountAmount(2000)
                .discountLimit(8000)
                .minimumOrderValue(0)
                .isExpired(0)
                .build();

        int result = couponDiscountCalculator.discount(originOrderPrice, coupon);
        assertThat(result).isEqualTo(8000);
    }

    @DisplayName("성공-정액할인-최대할인금액")
    @Test
    void fixedAmountDiscountLimitSuccess() {
        int originOrderPrice = 10000;
        Coupon coupon = Coupon.builder()
                .name("CouponName")
                .roundingPolicy(Coupon.RoundingPolicy.OFF)
                .roundingPlace(Coupon.RoundingPlace.TENS)
                .discountPolicy(Coupon.DiscountPolicy.FIXED_AMOUNT)
                .discountAmount(2000)
                .discountLimit(9000)
                .minimumOrderValue(0)
                .isExpired(0)
                .build();

        int result = couponDiscountCalculator.discount(originOrderPrice, coupon);
        assertThat(result).isEqualTo(9000);
    }

    @DisplayName("실패-최소주문금액 미달")
    @Test
    void fixedAmountFailed() {
        int originOrderPrice = 10000;
        Coupon coupon = Coupon.builder()
                .name("CouponName")
                .roundingPolicy(Coupon.RoundingPolicy.OFF)
                .roundingPlace(Coupon.RoundingPlace.TENS)
                .discountPolicy(Coupon.DiscountPolicy.FIXED_AMOUNT)
                .discountAmount(2000)
                .discountLimit(9000)
                .minimumOrderValue(11000)
                .isExpired(0)
                .build();

        assertThatThrownBy(() -> couponDiscountCalculator.discount(originOrderPrice, coupon))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("실패-정액할인 할인 단위 부적합")
    @Test
    void fixedAmountFormatFailed() {
        int originOrderPrice = 10000;
        Coupon coupon = Coupon.builder()
                .name("CouponName")
                .roundingPolicy(Coupon.RoundingPolicy.OFF)
                .roundingPlace(Coupon.RoundingPlace.TENS)
                .discountPolicy(Coupon.DiscountPolicy.FIXED_AMOUNT)
                .discountAmount(100)
                .discountLimit(9000)
                .minimumOrderValue(11000)
                .isExpired(0)
                .build();

        assertThatThrownBy(() -> couponDiscountCalculator.discount(originOrderPrice, coupon))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
