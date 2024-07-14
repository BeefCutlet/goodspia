package shop.goodspia.goods.api.member.dto;

import lombok.Builder;
import lombok.Getter;
import shop.goodspia.goods.api.member.entity.Gender;
import shop.goodspia.goods.global.common.validator.ValidEnum;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class MemberSaveRequest {

    @Email
    private String email;

    @Pattern(regexp = "[0-9a-zA-Z!@#$%]{8,20}")
    private String password;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String name;

    @ValidEnum(enumClass = Gender.class)
    private Gender gender;

    @Pattern(regexp = "^[0-9]{3}-[0-9]{3,4}-[0-9]{4}$")
    private String phoneNumber;

    @Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$")
    private String birthday;

    @Pattern(regexp = "^[0-9]{5}$")
    private String zipcode;

    @NotNull
    private String address1;

    @NotNull
    private String address2;

}
