package shop.goodspia.goods.api.wish.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WishSaveRequest {

    @Min(1)
    private Long goodsId;
}
