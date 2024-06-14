package shop.goodspia.goods.api.order.dto;

public enum OrderStatus {
    READY,//주문 준비 (결제 대기)
    COMPLETE,//주문 완료 (결제까지 완료)
    CANCEL,//결제 완료 이후 주문 취소
}
