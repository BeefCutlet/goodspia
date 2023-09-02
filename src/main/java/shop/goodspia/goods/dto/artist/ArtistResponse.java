package shop.goodspia.goods.dto.artist;

import lombok.Getter;
import shop.goodspia.goods.entity.AccountBank;
import shop.goodspia.goods.entity.Artist;

@Getter
public class ArtistResponse {

    private String profileImage;
    private String nickname;
    private String phoneNumber;
    private AccountBank accountBank;
    private String accountNumber;

    public ArtistResponse(Artist artist) {
        this.profileImage = artist.getProfileImage();
        this.nickname = artist.getNickname();
        this.phoneNumber = artist.getPhoneNumber();
        this.accountBank = artist.getAccountBank();
        this.accountNumber = artist.getAccountNumber();
    }
}
