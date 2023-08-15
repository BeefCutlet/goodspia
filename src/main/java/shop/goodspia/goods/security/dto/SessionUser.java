package shop.goodspia.goods.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionUser {

    private Long memberId;
    private Long artistId;
}
