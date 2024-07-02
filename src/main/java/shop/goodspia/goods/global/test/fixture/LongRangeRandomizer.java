package shop.goodspia.goods.global.test.fixture;

import org.jeasy.random.api.Randomizer;

class LongRangeRandomizer implements Randomizer<Long> {

    private final long min;
    private final long max;

    public LongRangeRandomizer(final long min, final long max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Long getRandomValue() {
        return min + (long) (Math.random() * (max - min));
    }
}
