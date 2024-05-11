package shop.goodspia.goods.wish.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.security.dto.MemberPrincipal;
import shop.goodspia.goods.wish.dto.CheckWishResponse;
import shop.goodspia.goods.wish.service.WishService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wish")
public class WishQueryController {

    private final WishService wishService;

    @GetMapping("/{goodsId}")
    public ResponseEntity<CheckWishResponse> checkWishExistence(@PathVariable Long goodsId,
                                                                @AuthenticationPrincipal MemberPrincipal principal) {
        Long memberId = principal != null ? principal.getId() : null;
        CheckWishResponse checkWishResponse = wishService.getWishStatus(memberId, goodsId);
        return ResponseEntity.ok(checkWishResponse);
    }
}
