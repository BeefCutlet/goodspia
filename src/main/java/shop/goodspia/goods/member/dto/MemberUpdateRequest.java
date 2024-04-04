package shop.goodspia.goods.member.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter @Setter
public class MemberUpdateRequest {

    @NotBlank
    @Pattern(regexp = "[0-9a-zA-Z!@#$%]{6,20}")
    private String password;

    @NotBlank
    @Pattern(regexp = "[0-9a-zA-Zㅏ-ㅣㄱ-ㅎ가-힣._]{2,12}")
    private String nickname;

    private String zipcode;
    private String address1;
    private String address2;

    @NotEmpty
    @Min(10)
    @Max(11)
    private String phoneNumber;

    @NotEmpty
    private String birthday;
}
