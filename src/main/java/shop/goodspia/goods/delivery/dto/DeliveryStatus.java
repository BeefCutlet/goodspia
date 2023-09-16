package shop.goodspia.goods.delivery.dto;

public enum DeliveryStatus {
    PRODUCT_READY("productReady"),
    DELIVERY_READY("deliveryReady"),
    DELIVERY_PROGRESSING("deliveryProgressing"),
    DELIVERY_COMPLETE("deliveryComplete");

    private final String status;

    DeliveryStatus(String status) {
        this.status = status;
    }

    //문자열 배송상태를 DeliveryStatus 객체로 변경 및 검증
    public static DeliveryStatus convertToDeliveryStatus(String status) {
        if (status == null) {
            throw new IllegalArgumentException("배송 상태가 입력되지 않았습니다.");
        }

        for (DeliveryStatus deliveryStatus : DeliveryStatus.values()) {
            if (deliveryStatus.status.equals(status)) {
                return deliveryStatus;
            }
        }

        throw new IllegalArgumentException("일치하는 배송 송태가 없습니다.");
    }
}
