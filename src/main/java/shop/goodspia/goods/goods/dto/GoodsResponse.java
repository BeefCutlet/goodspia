package shop.goodspia.goods.goods.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema
@Getter @Setter
public class GoodsResponse {

    @NotNull
    private Long goodsId;

    @NotBlank
    private String thumbnail;

    @NotBlank
    private String goodsName;

    @NotNull
    @Min(1)
    private int price;
}
