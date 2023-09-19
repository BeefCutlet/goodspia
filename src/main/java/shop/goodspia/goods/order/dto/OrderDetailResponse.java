package shop.goodspia.goods.order.dto;

import lombok.Getter;
import shop.goodspia.goods.order.entity.OrderGoods;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
public class OrderDetailResponse {

    @NotBlank
    @Pattern(regexp = "(ORDER_)([0-9a-zA-Z])+")
    private String orderUid;

    @PastOrPresent
    private LocalDateTime createdTime;

    @Min(1)
    private Long goodsId;

    @NotNull
    @Pattern(regexp = "[0-9a-zA-Z가-힣]")
    @Size(min = 1, max = 50)
    private String goodsName;

    @NotNull
    private String goodsSummary;

    private String goodsImage;

    public OrderDetailResponse(OrderGoods orderGoods) {
        this.orderUid = orderGoods.getOrders().getOrderUid();
        this.createdTime = orderGoods.getOrders().getCreatedTime();
        this.goodsId = orderGoods.getGoods().getId();
        this.goodsName = orderGoods.getGoods().getName();
        this.goodsSummary = orderGoods.getGoods().getSummary();
        this.goodsImage = orderGoods.getGoods().getThumbnail();
    }
}
