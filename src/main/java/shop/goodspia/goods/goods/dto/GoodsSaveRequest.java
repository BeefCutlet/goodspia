package shop.goodspia.goods.goods.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

@Getter @Setter
public class GoodsSaveRequest {

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

    @Min(1000)
    @Max(10000000)
    private int price;

    @NotNull
    private List<String> designs;
}
