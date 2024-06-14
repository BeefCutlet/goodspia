package shop.goodspia.goods.api.artist.dto;

import lombok.Getter;
import shop.goodspia.goods.global.common.dto.AccountBank;
import shop.goodspia.goods.global.common.validator.ValidEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
public class ArtistUpdateRequest {

    @NotEmpty
    private String nickname;

    @ValidEnum(enumClass = AccountBank.class)
    private AccountBank accountBank;

    @NotBlank
    private String accountNumber;

    @Pattern(regexp = "^[0-9]{3}-[0-9]{3,4}-[0-9]{4}$")
    private String phoneNumber;

}
