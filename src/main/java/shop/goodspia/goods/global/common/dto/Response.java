package shop.goodspia.goods.global.common.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class Response<T> {

    private final String message;
    private final T data;
}
