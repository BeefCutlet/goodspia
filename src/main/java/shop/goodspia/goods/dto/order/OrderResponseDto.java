package shop.goodspia.goods.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderResponseDto {

    private String merchantUid;
    private int quantity;
    private int totalPrice;
    private long goodsId;
    private String goodsName;
    private int goodsPrice;
    private String goodsImage;
    private String goodsDesign;
}
