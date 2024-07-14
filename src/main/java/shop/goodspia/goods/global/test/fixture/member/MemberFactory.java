package shop.goodspia.goods.global.test.fixture.member;

import org.jeasy.random.EasyRandom;
import shop.goodspia.goods.api.member.entity.Address;
import shop.goodspia.goods.api.member.entity.Member;
import shop.goodspia.goods.api.member.entity.MemberStatus;
import shop.goodspia.goods.global.test.fixture.EasyRandomUtils;

import java.util.HashMap;
import java.util.Map;

import static shop.goodspia.goods.global.test.fixture.RandomUtils.generateEmail;
import static shop.goodspia.goods.global.test.fixture.RandomUtils.generatePhoneNumber;

public class MemberFactory {

    public static Member createMember() {
        Map<String, Object> values = new HashMap<>();
        values.put("email", generateEmail());
        values.put("birthday", "2000-01-01");
        values.put("phoneNumber", generatePhoneNumber());
        values.put("address", Address.of(
                "01234",
                "역시 멋지구 시크할지동",
                "101호"
        ));
        values.put("status", MemberStatus.ACTIVE);
        EasyRandom instance = EasyRandomUtils.getInstance(values);

        return instance.nextObject(Member.class);
    }
}
