package shop.goodspia.goods.goods.dto.design;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class DesignResponse {

    @NotNull
    @Pattern(regexp = "[0-9a-zA-Z가-힣]")
    private String designName;

    public DesignResponse(String designName) {
        this.designName = designName;
    }
}
