package shop.goodspia.goods.dto.artist;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArtistRequestDto {
    private Long id;
    private String nickname;
    private String profileImage;
    private String accountBank;
    private String accountNumber;
    private String phoneNumber;
}
