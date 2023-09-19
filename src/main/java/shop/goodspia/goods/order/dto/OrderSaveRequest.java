package shop.goodspia.goods.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.*;

@Schema(name = "주문 등록 정보", description = "새로 저장할 주문의 정보")
@Getter
@AllArgsConstructor
public class OrderSaveRequest {

    @Schema(description = "굿즈의 수량")
    @Min(1)
    @Max(1000)
    private int quantity;

    @Schema(description = "굿즈의 총 금액")
    @Min(1000)
    @Max(100000000)
    private int totalPrice;

    @Schema(description = "저장할 굿즈의 번호")
    @Min(1)
    private Long goodsId;

    @Schema(description = "선택된 굿즈 디자인")
    @NotNull
    @Pattern(regexp = "[0-9a-zA-Z가-힣]")
    private String goodsDesign;
}
