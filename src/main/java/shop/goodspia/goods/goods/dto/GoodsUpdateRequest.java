package shop.goodspia.goods.goods.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

@Schema(name = "굿즈 정보", description = "기존에 등록된 굿즈의 수정된 정보")
@Getter @Setter
public class GoodsUpdateRequest {

    @Schema(description = "굿즈 이름")
    @NotNull
    @Pattern(regexp = "[0-9a-zA-Z가-힣]")
    @Size(min = 1, max = 50)
    private String name;

    @Schema(description = "굿즈 요약")
    @NotNull
    private String summary;

    @Schema(description = "굿즈 내용")
    @NotNull
    private String content;

    @Schema(description = "굿즈 카테고리")
    @NotNull
    private String category;

    @Schema(description = "굿즈 이미지")
    private String thumbnail;

    @Schema(description = "굿즈 가격")
    @NotNull
    @Min(1000)
    private int price;

    @Schema(description = "굿즈 디자인")
    @NotNull
    private List<String> designs;
}
