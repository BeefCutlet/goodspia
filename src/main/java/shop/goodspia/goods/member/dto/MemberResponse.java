package shop.goodspia.goods.member.dto;

import lombok.Builder;
import lombok.Getter;
import shop.goodspia.goods.member.entity.Address;
import shop.goodspia.goods.member.entity.Gender;
import shop.goodspia.goods.member.entity.Member;

@Getter
@Builder
public class MemberResponse {

    private String email;
    private String nickname;
    private String name;
    private Gender gender;
    private String phoneNumber;
    private String birthday;
    private Address address;

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .name(member.getName())
                .gender(member.getGender())
                .phoneNumber(member.getBirthday())
                .birthday(member.getBirthday())
                .address(member.getAddress())
                .build();
    }
}
