package shop.goodspia.goods.goods.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Schema
@Getter @Setter
public class GoodsResponse {

    @Min(1)
    private Long goodsId;

    private String thumbnail;

    @NotNull
    @Pattern(regexp = "[0-9a-zA-Z가-힣]")
    @Size(min = 1, max = 50)
    private String goodsName;

    @Min(1000)
    @Max(10000000)
    private int price;
}
