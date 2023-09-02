package shop.goodspia.goods.dto.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(name = "결제 사전등록 정보", description = "추후 검증을 위해 저장할 결제 사전등록 정보")
@Getter
public class PaymentPrepareRequest {

    @Schema(description = "주문 번호")
    @NotBlank
    private String merchantUid; //주문 번호

    @Schema(description = "결제 예정된 가격")
    @NotNull
    private BigDecimal amount; //주문 가격
}
