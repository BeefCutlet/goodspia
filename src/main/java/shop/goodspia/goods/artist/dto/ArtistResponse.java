package shop.goodspia.goods.artist.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import shop.goodspia.goods.artist.entity.Artist;
import shop.goodspia.goods.common.dto.AccountBank;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ArtistResponse {

    private String nickname;

    private String profileImage;

    private String phoneNumber;

    private AccountBank accountBank;

    private String accountNumber;

    public static ArtistResponse from(Artist artist) {
        return ArtistResponse.builder()
                .nickname(artist.getNickname())
                .phoneNumber(artist.getPhoneNumber())
                .profileImage(artist.getProfileImage())
                .accountBank(artist.getAccountBank())
                .accountNumber(artist.getAccountNumber())
                .build();
    }
}
