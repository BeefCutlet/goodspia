package shop.goodspia.goods.member.dto;

import lombok.Getter;
import lombok.Setter;
import shop.goodspia.goods.common.validator.ValidEnum;
import shop.goodspia.goods.member.entity.Gender;

import javax.validation.constraints.*;

@Getter @Setter
public class MemberSaveRequest {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "[0-9a-zA-Z!@#$%]{8,20}")
    private String password;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String name;

    @ValidEnum(enumClass = Gender.class)
    private Gender gender;

    @Pattern(regexp = "[0-9]{10,11}")
    private String phoneNumber;

    @Pattern(regexp = "[0-9]{8}")
    private String birthday;

    private String zipcode;
    private String address1;
    private String address2;

}
