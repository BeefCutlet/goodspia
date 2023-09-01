package shop.goodspia.goods.dto.artist;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class ArtistRequestDto {

    @NotNull
    private Long id;
    @NotBlank
    private String nickname;
    private String profileImage;
    @NotBlank
    private String accountBank;
    @NotBlank
    private String accountNumber;
    @NotBlank
    @Pattern(regexp = "^[0-9]{3}-[0-9]{3,4}-[0-9]{4}$")
    private String phoneNumber;
}
