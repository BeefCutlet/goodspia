package shop.goodspia.goods.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import shop.goodspia.goods.dto.ArtistDto;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    public static Artist createArtist(ArtistDto artistDto) {
        return Artist.builder()
                .nickname(artistDto.getNickname())
                .profileImage(artistDto.getProfileImage())
                .accountBank(AccountBank.covertToBank(artistDto.getAccountBank()))
                .accountNumber(artistDto.getAccountNumber())
                .phoneNumber(artistDto.getPhoneNumber())
                .build();
    }

    public void updateArtist(ArtistDto artistDto) {
        this.nickname = artistDto.getNickname();
        this.profileImage = artistDto.getProfileImage();
        this.accountBank = AccountBank.covertToBank(artistDto.getAccountBank());
        this.accountNumber = artistDto.getAccountNumber();
        this.phoneNumber = artistDto.getPhoneNumber();
    }

}
