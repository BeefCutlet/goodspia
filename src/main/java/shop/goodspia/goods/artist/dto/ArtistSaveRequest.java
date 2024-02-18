package shop.goodspia.goods.artist.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class ArtistSaveRequest {

    @NotBlank
    @Pattern(regexp = "[0-9a-zA-Zㅏ-ㅣㄱ-ㅎ가-힣._]{2,12}")
    private String nickname;

    @Pattern(regexp = "([^\\s]+(?=\\.(jpg|jpeg|gif|png))\\.\\2)")
    private String profileImage;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]")
    private String accountBank;

    @NotBlank
    private String accountNumber;

    @NotBlank
    @Pattern(regexp = "^[0-9]{3}-[0-9]{3,4}-[0-9]{4}$")
    private String phoneNumber;
}
