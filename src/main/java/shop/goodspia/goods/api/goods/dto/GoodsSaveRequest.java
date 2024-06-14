package shop.goodspia.goods.api.goods.dto;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
public class GoodsSaveRequest {

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

    @Size(min = 1)
    private List<String> designs;
}
