package shop.goodspia.goods.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class UrlResponse {

    private final String url;
}
