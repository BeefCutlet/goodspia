package shop.goodspia.goods.entity;

import lombok.*;
import shop.goodspia.goods.dto.member.MemberRequestDto;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    private LocalDateTime lastPasswordChanged;
    private String nickname;
    private String lastLoginIp;
    private int isWithdraw;
    private LocalDateTime withdrawTime;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    public static Member createMember(MemberRequestDto memberRequestDto) {
        return Member.builder()
                .email(memberRequestDto.getEmail())
                .password(memberRequestDto.getPassword())
                .nickname(memberRequestDto.getNickname())
                .build();
    }

    public void registerArtist(Artist artist) {
        this.artist = artist;
    }

    public void updateMember(MemberRequestDto memberRequestDto) {
        this.password = memberRequestDto.getPassword();
        this.nickname = memberRequestDto.getNickname();
    }
}
