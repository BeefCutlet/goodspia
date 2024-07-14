package shop.goodspia.goods.global.test.fixture.member;

import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import shop.goodspia.goods.api.member.dto.MemberSaveRequest;
import shop.goodspia.goods.global.test.fixture.EasyRandomUtils;

import java.util.HashMap;
import java.util.Map;

import static shop.goodspia.goods.global.test.fixture.RandomUtils.generateEmail;
import static shop.goodspia.goods.global.test.fixture.RandomUtils.generatePhoneNumber;

@Slf4j
public class MemberDtoFactory {

    public static MemberSaveRequest createMemberSaveRequest() {
        Map<String, Object> values = new HashMap<>();
        values.put("email", generateEmail());
        values.put("password", "asdqwe123!");
        values.put("phoneNumber", generatePhoneNumber());
        values.put("birthday", "2000-01-01");
        values.put("zipcode", "01234");
        values.put("address1", "역시 멋지구 시크할지동");
        values.put("address2", "101호");
        EasyRandom instance = EasyRandomUtils.getInstance(values);

        MemberSaveRequest memberSaveRequest = instance.nextObject(MemberSaveRequest.class);
        log.info("email: {}", memberSaveRequest.getEmail());
        log.info("password: {}", memberSaveRequest.getPassword());
        log.info("nickname: {}", memberSaveRequest.getNickname());
        log.info("name: {}", memberSaveRequest.getName());
        log.info("gender: {}", memberSaveRequest.getGender());
        log.info("phoneNumber: {}", memberSaveRequest.getPhoneNumber());
        log.info("birthday: {}", memberSaveRequest.getBirthday());
        log.info("zipcode: {}", memberSaveRequest.getZipcode());
        log.info("address1: {}", memberSaveRequest.getAddress1());
        log.info("address2: {}", memberSaveRequest.getAddress2());

        return memberSaveRequest;
    }
}
