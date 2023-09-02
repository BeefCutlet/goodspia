package shop.goodspia.goods.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Schema(name = "장바구니 등록 굿즈 정보", description = "장바구니에 등록할 굿즈의 정보")
@Getter @Setter
public class CartSaveRequest {

    @Schema(description = "굿즈 수량")
    @NotNull
    @Min(1)
    private int quantity;

    @Schema(description = "회원 번호")
    @NotNull
    @Min(1)
    private Long memberId;

    @Schema(description = "굿즈 번호")
    @NotNull
    @Min(1)
    private Long goodsId;

    @Schema(description = "굿즈 디자인 번호")
    @NotNull
    @Min(1)
    private Long designId;
}
