package shop.goodspia.goods.member.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import shop.goodspia.goods.common.exception.InvalidRequestException;
import shop.goodspia.goods.common.exception.dto.ErrorCode;

public enum Gender {
    MAN("남성"),
    WOMAN("여성")
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
