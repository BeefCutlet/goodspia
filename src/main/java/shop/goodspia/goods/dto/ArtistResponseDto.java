package shop.goodspia.goods.dto;

import lombok.Builder;
import lombok.Getter;
import shop.goodspia.goods.entity.AccountBank;

@Getter
@Builder
public class ArtistResponseDto {

    private String nickname;
    private String profileImage;
    private String accountBank;
    private String accountNumber;
    private String phoneNumber;
}
