package shop.goodspia.goods.dto.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class MemberUpdateRequest {

    private Long id;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;
}
