package shop.goodspia.goods.artist.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import shop.goodspia.goods.artist.dto.ArtistSaveRequest;
import shop.goodspia.goods.artist.dto.ArtistUpdateRequest;
import shop.goodspia.goods.common.dto.AccountBank;
import shop.goodspia.goods.common.entity.BaseTimeEntity;
import shop.goodspia.goods.member.entity.Member;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Slf4j
public class Artist extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private Long id;
    private String nickname;
    private String phoneNumber;
    private String profileImage;
    @Enumerated(EnumType.STRING)
    private AccountBank accountBank;
    private String accountNumber;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Artist from(Member member, ArtistSaveRequest artistSaveRequest) {
        return Artist.builder()
                .nickname(artistSaveRequest.getNickname())
                .phoneNumber(artistSaveRequest.getPhoneNumber())
                .accountBank(artistSaveRequest.getAccountBank())
                .accountNumber(artistSaveRequest.getAccountNumber())
                .member(member)
                .build();
    }

    public void updateArtist(ArtistUpdateRequest artistUpdateRequest, String profileImage) {
        this.nickname = artistUpdateRequest.getNickname();
        this.accountBank = artistUpdateRequest.getAccountBank();
        this.accountNumber = artistUpdateRequest.getAccountNumber();
        this.phoneNumber = artistUpdateRequest.getPhoneNumber();
        if (profileImage != null) {
            this.profileImage = profileImage;
        }
    }

}
