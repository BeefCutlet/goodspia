package shop.goodspia.goods.goods.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter @Setter
public class GoodsUpdateRequest {

    @NotNull
    @Pattern(regexp = "[0-9a-zA-Z가-힣]")
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    private String summary;

    @NotNull
    private String content;

    @NotNull
    private String category;

    private String thumbnail;

    @NotNull
    @Min(1000)
    private int price;

    @NotNull
    private List<String> designs;
}
