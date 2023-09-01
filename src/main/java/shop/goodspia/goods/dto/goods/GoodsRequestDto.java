package shop.goodspia.goods.dto.goods;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @Setter
public class GoodsRequestDto {

    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String summary;
    @NotBlank
    private String content;
    @NotBlank
    private String category;
    @NotBlank
    private String thumbnail;
    @NotNull
    @Min(1000)
    private int price;
    @NotNull
    private List<String> designs;
}
