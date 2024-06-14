package shop.goodspia.goods.api.member.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import shop.goodspia.goods.global.common.exception.InvalidRequestException;
import shop.goodspia.goods.global.common.exception.dto.ErrorCode;

public enum Gender {
    MALE("남성"),
    FEMALE("여성"),
    ;

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    @JsonCreator
    public static Gender from(String requestGender) {
        for (Gender gender : Gender.values()) {
            if (gender.name().equals(requestGender)) {
                return gender;
            }
        }
        throw new InvalidRequestException(ErrorCode.INVALID_GENDER);
    }

    public String getDescription() {
        return description;
    }
}
