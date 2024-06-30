package shop.goodspia.goods.api.member.entity;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import shop.goodspia.goods.global.common.entity.BaseTimeEntity;
import shop.goodspia.goods.api.member.dto.MemberSaveRequest;
import shop.goodspia.goods.api.member.dto.MemberUpdateRequest;

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
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String phoneNumber;
    private String birthday;
    @Embedded
    private Address address;
    @Enumerated(EnumType.STRING)
    private MemberStatus status;
    @Enumerated(EnumType.STRING)
    private Role role;
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
                .role(Role.USER)
                .build();
    }

    public void updateMember(MemberUpdateRequest memberUpdateRequest) {
        this.name = memberUpdateRequest.getName();
        this.nickname = memberUpdateRequest.getNickname();
        this.phoneNumber = memberUpdateRequest.getPhoneNumber();
        this.birthday = memberUpdateRequest.getBirthday();
    }

    public enum Role {
        USER("ROLE_USER"), ADMIN("ROLE_ADMIN"),
        ;

        private final String roleName;

        Role(final String roleName) {
            this.roleName = roleName;
        }

        public String getRoleName() {
            return roleName;
        }
    }
}
