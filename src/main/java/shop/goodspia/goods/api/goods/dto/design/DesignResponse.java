package shop.goodspia.goods.api.goods.dto.design;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DesignResponse {

    private final Long designId;

    private final String designName;
}
