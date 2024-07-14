package shop.goodspia.goods.global.test.fixture;

import org.jeasy.random.randomizers.text.StringRandomizer;

import java.util.Random;

public class RandomUtils {

    public static String generateEmail() {
        Random random = new Random();
        org.jeasy.random.randomizers.text.StringRandomizer randomizer = new StringRandomizer(3, 15, random.nextInt());
        String email = randomizer.getRandomValue();
        return email + "@email.com";
    }

    public static String generatePhoneNumber() {
        FourDigitRandomizer randomizer = new FourDigitRandomizer();
        String firstValue = String.format("%04d", randomizer.getRandomValue());
        String lastValue = String.format("%04d", randomizer.getRandomValue());
        return "010-" + firstValue + "-" + lastValue;
    }
}
