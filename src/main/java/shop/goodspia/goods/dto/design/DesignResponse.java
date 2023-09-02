package shop.goodspia.goods.dto.design;

import lombok.Getter;

@Getter
public class DesignResponse {

    private String designName;

    public DesignResponse(String designName) {
        this.designName = designName;
    }
}
