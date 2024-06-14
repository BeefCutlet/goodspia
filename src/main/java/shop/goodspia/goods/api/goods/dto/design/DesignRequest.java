package shop.goodspia.goods.api.goods.dto.design;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class DesignRequest {

    @Min(1)
    private Long designId;

    @NotNull
    private String designName;
}
