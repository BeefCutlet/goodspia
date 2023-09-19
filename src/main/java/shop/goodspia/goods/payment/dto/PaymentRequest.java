package shop.goodspia.goods.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.*;

@Schema(name = "등록 결제 정보", description = "결제 API에서 결제 완료 후 검증하여 DB에 등록할 결제 정보")
@Getter
@Builder
public class PaymentRequest {

    @Schema(description = "결제 API에서 결제 완료 후 생성된 결제 UID")
    @NotNull
    private String paymentUid;

    @Schema(description = "결제 완료된 주문의 UID")
    @NotBlank
    @Pattern(regexp = "(ORDER_)([0-9a-zA-Z])+")
    private String orderUid;

    @Schema(description = "결제된 주문의 총 금액")
    @Min(1000)
    @Max(100000000)
    private int amount;

    @Schema(description = "결제한 카드의 은행사 코드")
    @NotNull
    private String cardBank;

    @Schema(description = "결제한 카드의 번호")
    @NotNull
    @CreditCardNumber
    private String cardNumber;
}
