package shop.goodspia.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IAmPortResponseDto {

    private String merchant_uid;
    private int amount;
}