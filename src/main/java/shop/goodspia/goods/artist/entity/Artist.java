package shop.goodspia.goods.artist.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import shop.goodspia.goods.artist.dto.ArtistSaveRequest;
import shop.goodspia.goods.artist.dto.ArtistUpdateRequest;
import shop.goodspia.goods.common.dto.AccountBank;
import shop.goodspia.goods.common.entity.BaseTimeEntity;

import javax.persistence.*;

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
    private String profileImage;
    @Enumerated(EnumType.STRING)
    private AccountBank accountBank;
    private String accountNumber;
    private String phoneNumber;

    public static Artist createArtist(ArtistSaveRequest artistSaveRequest) {
        return Artist.builder()
                .nickname(artistSaveRequest.getNickname())
                .profileImage(artistSaveRequest.getProfileImage())
                .accountBank(AccountBank.convertStringToBank(artistSaveRequest.getAccountBank()))
                .accountNumber(artistSaveRequest.getAccountNumber())
                .phoneNumber(artistSaveRequest.getPhoneNumber())
                .build();
    }

    public void updateArtist(ArtistUpdateRequest artistUpdateRequest) {
        this.nickname = artistUpdateRequest.getNickname();
        this.profileImage = artistUpdateRequest.getProfileImage();
        this.accountBank = AccountBank.convertStringToBank(artistUpdateRequest.getAccountBank());
        this.accountNumber = artistUpdateRequest.getAccountNumber();
        this.phoneNumber = artistUpdateRequest.getPhoneNumber();
    }

}
