package shop.goodspia.goods.cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Schema(name = "장바구니 등록 굿즈 정보", description = "장바구니에 등록할 굿즈의 정보")
@Getter
@AllArgsConstructor
public class CartSaveRequest {

    @Schema(description = "굿즈 수량")
    @Min(1)
    @Max(1000)
    private int quantity;

    @Schema(description = "굿즈 번호")
    @Min(1)
    private Long goodsId;

    @Schema(description = "굿즈 디자인 번호")
    @Min(1)
    private Long designId;
}
