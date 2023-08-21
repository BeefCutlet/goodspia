package shop.goodspia.goods.entity;

import lombok.*;
import shop.goodspia.goods.dto.MemberDto;

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

    public static Member createMember(MemberDto memberDto) {
        return Member.builder()
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .nickname(memberDto.getNickname())
                .build();
    }

    public void registerArtist(Artist artist) {
        this.artist = artist;
    }

    public void updateMember(MemberDto memberDto) {
        this.password = memberDto.getPassword();
        this.nickname = memberDto.getNickname();
    }
}
