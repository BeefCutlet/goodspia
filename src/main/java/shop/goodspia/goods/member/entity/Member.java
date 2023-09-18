package shop.goodspia.goods.member.entity;

import lombok.*;
import shop.goodspia.goods.artist.entity.Artist;
import shop.goodspia.goods.common.entity.BaseTimeEntity;
import shop.goodspia.goods.member.dto.MemberSaveRequest;
import shop.goodspia.goods.member.dto.MemberUpdateRequest;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

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
    private int isWithdraw;
    private LocalDateTime withdrawTime;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    public static Member createMember(MemberSaveRequest memberSaveRequest) {
        return Member.builder()
                .email(memberSaveRequest.getEmail())
                .password(memberSaveRequest.getPassword())
                .nickname(memberSaveRequest.getNickname())
                .isWithdraw(0)
                .build();
    }

    public void registerArtist(Artist artist) {
        this.artist = artist;
    }

    public void updateMember(MemberUpdateRequest memberUpdateRequest) {
        this.password = memberUpdateRequest.getPassword();
        this.nickname = memberUpdateRequest.getNickname();
    }
}
