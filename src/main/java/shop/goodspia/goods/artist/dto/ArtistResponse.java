package shop.goodspia.goods.artist.dto;

import lombok.Getter;
import shop.goodspia.goods.common.dto.AccountBank;
import shop.goodspia.goods.artist.entity.Artist;

@Getter
public class ArtistResponse {

    private String profileImage;
    private String nickname;
    private String phoneNumber;
    private AccountBank accountBank;
    private String accountNumber;

    public ArtistResponse(Artist artist) {
        this.profileImage = artist.getProfileImage().split("-")[1];
        this.nickname = artist.getNickname();
        this.phoneNumber = artist.getPhoneNumber();
        this.accountBank = artist.getAccountBank();
        this.accountNumber = artist.getAccountNumber();
    }
}
