package shop.goodspia.goods.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDto {

    private Long id;
    private String email;
    private String password;
    private String nickname;
}
