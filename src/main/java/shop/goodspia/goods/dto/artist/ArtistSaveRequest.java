package shop.goodspia.goods.dto.artist;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Schema(name = "아티스트 정보", description = "아티스트 등록을 위한 정보를 설정합니다.")
@Getter
@Setter
public class ArtistSaveRequest {

    @Schema(name = "아티스트용 닉네임", example = "닉네임")
    @NotBlank
    private String nickname;

    @Schema(name = "아티스트 프로필 이미지", example = "profile-image.png")
    private String profileImage;

    @Schema(name = "정산 시 입금받을 계좌의 은행", example = "KB")
    @NotBlank
    private String accountBank;

    @Schema(name = "정산 시 입금받을 계좌의 번호", example = "123123-00-123123")
    @NotBlank
    private String accountNumber;

    @Schema(name = "문의를 받을 전화번호", example = "010-1234-5678")
    @NotBlank
    @Pattern(regexp = "^[0-9]{3}-[0-9]{3,4}-[0-9]{4}$")
    private String phoneNumber;
}
