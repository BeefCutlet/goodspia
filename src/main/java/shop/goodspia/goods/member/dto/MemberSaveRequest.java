package shop.goodspia.goods.member.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter @Setter
public class MemberSaveRequest {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "[0-9a-zA-Z!@#$%]{6,20}")
    private String password;

    @NotEmpty
    @Pattern(regexp = "[0-9a-zA-Zㅏ-ㅣㄱ-ㅎ가-힣._]{2,12}")
    private String nickname;

    @NotEmpty
    private String name;

    private String zipcode;
    private String address1;
    private String address2;

    @NotEmpty
    @Min(10)
    @Max(11)
    private String phoneNumber;

    @NotEmpty
    @Min(8)
    @Max(8)
    private String birthday;
}
