package shop.goodspia.goods.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import shop.goodspia.goods.dto.artist.ArtistRequestDto;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Slf4j
public class Artist extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "artist_id")
    private Long id;
    private String nickname;
    private String profileImage;
    @Enumerated(EnumType.STRING)
    private AccountBank accountBank;
    private String accountNumber;
    private String phoneNumber;

    public static Artist createArtist(ArtistRequestDto artistRequestDto) {
        return Artist.builder()
                .nickname(artistRequestDto.getNickname())
                .profileImage(artistRequestDto.getProfileImage())
                .accountBank(AccountBank.covertToBank(artistRequestDto.getAccountBank()))
                .accountNumber(artistRequestDto.getAccountNumber())
                .phoneNumber(artistRequestDto.getPhoneNumber())
                .build();
    }

    public void updateArtist(ArtistRequestDto artistRequestDto) {
        this.nickname = artistRequestDto.getNickname();
        this.profileImage = artistRequestDto.getProfileImage();
        this.accountBank = AccountBank.covertToBank(artistRequestDto.getAccountBank());
        this.accountNumber = artistRequestDto.getAccountNumber();
        this.phoneNumber = artistRequestDto.getPhoneNumber();
    }

}
