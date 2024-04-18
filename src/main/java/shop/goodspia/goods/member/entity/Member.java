package shop.goodspia.goods.member.entity;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import shop.goodspia.goods.common.entity.BaseTimeEntity;
import shop.goodspia.goods.member.dto.MemberSaveRequest;
import shop.goodspia.goods.member.dto.MemberUpdateRequest;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    private LocalDateTime lastPasswordChanged;
    private String nickname;
    private String name;
    private Gender gender;
    private String phoneNumber;
    private String birthday;
    @Embedded
    private Address address;
    @Enumerated(EnumType.STRING)
    private MemberStatus status;
    private LocalDateTime withdrawTime;

    public static Member from(MemberSaveRequest memberSaveRequest, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(memberSaveRequest.getEmail())
                .password(passwordEncoder.encode(memberSaveRequest.getPassword()))
                .nickname(memberSaveRequest.getNickname())
                .name(memberSaveRequest.getName())
                .gender(memberSaveRequest.getGender())
                .birthday(memberSaveRequest.getBirthday())
                .phoneNumber(memberSaveRequest.getPhoneNumber())
                .address(Address.of(
                        memberSaveRequest.getZipcode(),
                        memberSaveRequest.getAddress1(),
                        memberSaveRequest.getAddress2()))
                .status(MemberStatus.ACTIVE)
                .build();
    }

//    public void registerArtist(Artist artist) {
//        this.artist = artist;
//    }

    public void updateMember(MemberUpdateRequest memberUpdateRequest) {
        this.name = memberUpdateRequest.getName();
        this.nickname = memberUpdateRequest.getNickname();
        this.phoneNumber = memberUpdateRequest.getPhoneNumber();
        this.birthday = memberUpdateRequest.getBirthday();
    }
}
