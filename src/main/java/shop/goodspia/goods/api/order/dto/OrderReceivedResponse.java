package shop.goodspia.goods.api.order.dto;

import lombok.*;
import shop.goodspia.goods.api.member.entity.Address;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderReceivedResponse {

    private int orderQuantity;
    private int orderPrice;
    private String goodsName;
    private int goodsPrice;
    private LocalDateTime orderDate;
    private String memberNickname;
    private Address address;
}
