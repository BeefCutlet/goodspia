package shop.goodspia.goods.api.member.dto;

import lombok.Builder;
import lombok.Getter;
import shop.goodspia.goods.api.member.entity.Address;
import shop.goodspia.goods.api.member.entity.Gender;
import shop.goodspia.goods.api.member.entity.Member;

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
                .phoneNumber(member.getPhoneNumber())
                .birthday(member.getBirthday())
                .address(member.getAddress())
                .build();
    }
}
