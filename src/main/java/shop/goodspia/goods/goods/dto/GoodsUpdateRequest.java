package shop.goodspia.goods.goods.dto;

import lombok.Getter;
import shop.goodspia.goods.goods.dto.design.DesignRequest;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
public class GoodsUpdateRequest {

    @Min(1)
    private Long goodsId;

    @NotNull
    private String name;

    @Min(0)
    private int price;

    @Min(0)
    private int stock;

    @NotNull
    private Boolean isLimited;

    @NotNull
    private String material;

    @NotNull
    private String size;

    @Valid
    @Size(min = 1)
    private List<DesignRequest> designs;
}
