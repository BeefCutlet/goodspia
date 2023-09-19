package shop.goodspia.goods.artist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Schema(name = "아티스트 정보", description = "새로 등록할 아티스트의 정보")
@Getter
@Setter
public class ArtistSaveRequest {

    @Schema(description = "아티스트용 닉네임", example = "닉네임")
    @NotBlank
    @Pattern(regexp = "[0-9a-zA-Zㅏ-ㅣㄱ-ㅎ가-힣._]{2,12}")
    private String nickname;

    @Schema(description = "아티스트 프로필 이미지", example = "profile-image.png")
    @Pattern(regexp = "([^\\s]+(?=\\.(jpg|jpeg|gif|png))\\.\\2)")
    private String profileImage;

    @Schema(description = "정산 시 입금받을 계좌의 은행", example = "KB")
    @NotNull
    @Pattern(regexp = "[a-zA-Z]")
    private String accountBank;

    @Schema(description = "정산 시 입금받을 계좌의 번호", example = "123123-00-123123")
    @NotBlank
    private String accountNumber;

    @Schema(description = "문의를 받을 전화번호", example = "010-1234-5678")
    @NotBlank
    @Pattern(regexp = "^[0-9]{3}-[0-9]{3,4}-[0-9]{4}$")
    private String phoneNumber;
}
