package shop.goodspia.goods.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class Response<T> {

    private final int code;
    private final String message;
    private final T data;
}
