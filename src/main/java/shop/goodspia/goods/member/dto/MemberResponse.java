package shop.goodspia.goods.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class MemberResponse {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "[0-9a-zA-Zㅏ-ㅣㄱ-ㅎ가-힣._]{2,12}")
    private String nickname;
}
