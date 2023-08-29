package shop.goodspia.goods.dto.design;

import lombok.Getter;

@Getter
public class DesignResponseDto {

    private String designName;

    public DesignResponseDto(String designName) {
        this.designName = designName;
    }
}
