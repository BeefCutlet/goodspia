package shop.goodspia.goods.api.member.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
public class MemberUpdateRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String nickname;

    @Pattern(regexp = "^[0-9]{3}-[0-9]{3,4}-[0-9]{4}$")
    private String phoneNumber;

    @Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$")
    private String birthday;
}
