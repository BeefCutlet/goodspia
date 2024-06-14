package shop.goodspia.goods.global.security.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.goodspia.goods.api.member.entity.Member;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberPrincipal {

    private Long id;
    private String email;

    public static MemberPrincipal from(Member member) {
        return new MemberPrincipal(member.getId(), member.getEmail());
    }
}
