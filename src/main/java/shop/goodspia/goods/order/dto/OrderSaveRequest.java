package shop.goodspia.goods.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(name = "주문 등록 정보", description = "새로 저장할 주문의 정보")
@Getter
@AllArgsConstructor
public class OrderSaveRequest {

    @Schema(description = "굿즈의 수량")
    @NotNull
    @Min(1)
    private int quantity;

    @Schema(description = "굿즈의 총 금액")
    @NotNull
    @Min(1000)
    private int totalPrice;

    @Schema(description = "저장할 굿즈의 번호")
    @NotNull
    @Min(1)
    private Long goodsId;

    @Schema(description = "선택된 굿즈 디자인")
    @NotBlank
    private String goodsDesign;
}
