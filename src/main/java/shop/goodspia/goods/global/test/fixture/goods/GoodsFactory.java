package shop.goodspia.goods.global.test.fixture.goods;

import org.jeasy.random.EasyRandom;
import shop.goodspia.goods.api.artist.entity.Artist;
import shop.goodspia.goods.api.goods.entity.Goods;
import shop.goodspia.goods.global.test.fixture.EasyRandomUtils;

import java.util.HashMap;
import java.util.Map;

public class GoodsFactory {

    public static Goods createGoods(Artist artist) {
        Map<String, Object> values = new HashMap<>();
        values.put("price", 10000);
        values.put("stock", 100);
        values.put("wishCount", 0);
        values.put("isLimited", 1);
        values.put("isDeleted", 0);
        values.put("artist", artist);
        EasyRandom instance = EasyRandomUtils.getInstance(values);

        return instance.nextObject(Goods.class);
    }
}