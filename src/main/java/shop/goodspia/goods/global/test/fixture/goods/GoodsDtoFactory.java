package shop.goodspia.goods.global.test.fixture.goods;

import org.jeasy.random.EasyRandom;
import shop.goodspia.goods.api.goods.dto.GoodsUpdateRequest;
import shop.goodspia.goods.api.goods.dto.design.DesignRequest;
import shop.goodspia.goods.api.member.dto.MemberSaveRequest;
import shop.goodspia.goods.global.test.fixture.EasyRandomUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static shop.goodspia.goods.global.test.fixture.RandomUtils.generateEmail;
import static shop.goodspia.goods.global.test.fixture.RandomUtils.generatePhoneNumber;

public class GoodsDtoFactory {

    public static GoodsUpdateRequest createGoodsUpdateRequest() {
        List<DesignRequest> designRequestList = createDesignRequestList();

        Map<String, Object> values = new HashMap<>();
        values.put("goodsId", "asdqwe123!");
        values.put("name", generateEmail());
        values.put("price", generatePhoneNumber());
        values.put("stock", "2000-01-01");
        values.put("isLimited", "01234");
        values.put("material", "역시 멋지구 시크할지동");
        values.put("size", "101호");
        values.put("designs", designRequestList);
        EasyRandom instance = EasyRandomUtils.getInstance(values);

        return instance.nextObject(GoodsUpdateRequest.class);
    }

    private static List<DesignRequest> createDesignRequestList() {
        DesignRequest designRequest1 = createDesignRequest();
        DesignRequest designRequest2 = createDesignRequest();
        DesignRequest designRequest3 = createDesignRequest();

        List<DesignRequest> designRequests = new ArrayList<>();
        designRequests.add(designRequest1);
        designRequests.add(designRequest2);
        designRequests.add(designRequest3);

        return designRequests;
    }

    private static DesignRequest createDesignRequest() {
        EasyRandom instance = EasyRandomUtils.getInstance();
        return instance.nextObject(DesignRequest.class);
    }
}
