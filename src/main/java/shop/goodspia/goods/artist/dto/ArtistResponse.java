package shop.goodspia.goods.artist.dto;

import lombok.Getter;
import shop.goodspia.goods.common.dto.AccountBank;
import shop.goodspia.goods.artist.entity.Artist;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class ArtistResponse {

    @NotNull
    @Pattern(regexp = "[0-9a-zA-Zㅏ-ㅣㄱ-ㅎ가-힣._]{2,12}")
    private String nickname;

    @NotNull
    @Pattern(regexp = "([^\\s]+(?=\\.(jpg|jpeg|gif|png))\\.\\2)")
    private String profileImage;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]")
    private AccountBank accountBank;

    @NotBlank
    private String accountNumber;

    @NotBlank
    @Pattern(regexp = "^[0-9]{3}-[0-9]{3,4}-[0-9]{4}$")
    private String phoneNumber;

    public ArtistResponse(Artist artist) {
        this.nickname = artist.getNickname();
        this.profileImage = artist.getProfileImage().split("-")[1];
        this.accountBank = artist.getAccountBank();
        this.accountNumber = artist.getAccountNumber();
        this.phoneNumber = artist.getPhoneNumber();
    }
}
