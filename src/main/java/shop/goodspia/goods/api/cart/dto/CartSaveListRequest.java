package shop.goodspia.goods.api.cart.dto;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
public class CartSaveListRequest {

    @Valid
    @Size(min = 1)
    private List<CartSaveRequest> cartList;
}
