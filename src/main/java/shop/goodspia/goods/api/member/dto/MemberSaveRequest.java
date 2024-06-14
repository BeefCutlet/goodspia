package shop.goodspia.goods.api.member.dto;

import lombok.Getter;
import lombok.Setter;
import shop.goodspia.goods.api.member.entity.Gender;
import shop.goodspia.goods.global.common.validator.ValidEnum;

import javax.validation.constraints.*;

@Getter @Setter
public class MemberSaveRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
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

    private String zipcode;
    private String address1;
    private String address2;

}
