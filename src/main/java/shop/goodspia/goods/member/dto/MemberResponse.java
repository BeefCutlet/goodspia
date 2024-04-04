package shop.goodspia.goods.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import shop.goodspia.goods.member.entity.Member;

@Getter
@Builder
public class MemberResponse {

    private String email;
    private String nickname;
    private String name;
    private String phoneNumber;
    private String birthday;

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .name(member.getName())
                .phoneNumber(member.getBirthday())
                .birthday(member.getBirthday())
                .build();
    }
}
