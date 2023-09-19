package shop.goodspia.goods.goods.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Schema
@Getter @Setter
public class GoodsResponse {

    @NotNull
    private Long goodsId;

    @NotNull
    private String thumbnail;

    @NotNull
    @Pattern(regexp = "[a-zA-Z가-힣]{1,50}")
    private String goodsName;

    @NotNull
    @Min(1)
    private int price;
}
