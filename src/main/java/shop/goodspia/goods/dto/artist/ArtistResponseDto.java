package shop.goodspia.goods.dto.artist;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArtistResponseDto {

    private String nickname;
    private String profileImage;
    private String accountBank;
    private String accountNumber;
    private String phoneNumber;
}
