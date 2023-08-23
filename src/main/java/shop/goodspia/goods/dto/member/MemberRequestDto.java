package shop.goodspia.goods.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberRequestDto {

    private Long id;
    private String email;
    private String password;
    private String nickname;
}
