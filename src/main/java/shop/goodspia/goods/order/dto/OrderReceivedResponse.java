package shop.goodspia.goods.order.dto;

import lombok.Builder;
import lombok.Getter;
import shop.goodspia.goods.common.entity.Address;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Builder
public class OrderReceivedResponse {

    @Min(1)
    private Long goodsId;

    @NotNull
    @Pattern(regexp = "[0-9a-zA-Z가-힣]")
    @Size(min = 1, max = 50)
    private String goodsName;

    @NotNull
    @Pattern(regexp = "[0-9a-zA-Z가-힣]")
    private String goodsDesign;

    @Min(1000)
    @Max(10000000)
    private int goodsPrice;

    @NotBlank
    private String memberNickname;

    @PastOrPresent
    private LocalDateTime paymentTime;

    @Min(1)
    @Max(1000)
    private int quantity;

    @Min(1000)
    @Max(100000000)
    private int totalPrice;

    private Address address;
}
