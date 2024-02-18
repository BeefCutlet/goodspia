package shop.goodspia.goods.member.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class MemberSaveRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "[0-9a-zA-Z!@#$%]{6,20}")
    private String password;

    @NotBlank
    @Pattern(regexp = "[0-9a-zA-Zㅏ-ㅣㄱ-ㅎ가-힣._]{2,12}")
    private String nickname;
}
