package shop.goodspia.goods.order.dto;

import lombok.Builder;
import lombok.Getter;
import shop.goodspia.goods.common.entity.Address;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderReceivedResponse {

    private Long goodsId;

    private String goodsName;

    private String goodsDesign;

    private int goodsPrice;

    private String memberNickname;

    private LocalDateTime paymentTime;

    private int quantity;

    private int totalPrice;

    private Address address;
}
