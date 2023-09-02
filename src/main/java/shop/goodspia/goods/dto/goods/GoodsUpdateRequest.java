package shop.goodspia.goods.dto.goods;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(name = "굿즈 정보", description = "기존에 등록된 굿즈의 수정된 정보")
@Getter @Setter
public class GoodsUpdateRequest {

    @Schema(description = "굿즈 번호")
    @NotNull
    private Long id;

    @Schema(description = "굿즈 이름")
    @NotBlank
    private String name;

    @Schema(description = "굿즈 요약")
    @NotBlank
    private String summary;

    @Schema(description = "굿즈 내용")
    @NotBlank
    private String content;

    @Schema(description = "굿즈 카테고리")
    @NotBlank
    private String category;

    @Schema(description = "굿즈 이미지")
    @NotBlank
    private String thumbnail;

    @Schema(description = "굿즈 가격")
    @NotNull
    @Min(1000)
    private int price;

    @Schema(description = "굿즈 디자인")
    @NotNull
    private List<String> designs;
}
