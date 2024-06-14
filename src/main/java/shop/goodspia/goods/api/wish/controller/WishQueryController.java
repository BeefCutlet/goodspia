package shop.goodspia.goods.api.wish.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.api.wish.dto.CheckWishResponse;
import shop.goodspia.goods.api.wish.dto.WishListResponse;
import shop.goodspia.goods.api.wish.service.WishService;
import shop.goodspia.goods.global.security.dto.MemberPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wish")
public class WishQueryController {

    private final WishService wishService;

    /**
     * 현재 사용자가 특정 굿즈를 찜했는지 상태 확인
     */
    @GetMapping("/{goodsId}")
    public ResponseEntity<CheckWishResponse> checkWishExistence(@PathVariable Long goodsId,
                                                                @AuthenticationPrincipal MemberPrincipal principal) {
        Long memberId = principal != null ? principal.getId() : null;
        CheckWishResponse checkWishResponse = wishService.getWishStatus(memberId, goodsId);
        return ResponseEntity.ok(checkWishResponse);
    }

    /**
     * 현재 사용자가 찜한 굿즈 목록 조회
     */
    @GetMapping
    public ResponseEntity<WishListResponse> getWishList(@AuthenticationPrincipal MemberPrincipal principal) {
        Long memberId = principal.getId();
        WishListResponse wishList = wishService.getWishList(memberId);
        return ResponseEntity.ok(wishList);
    }
}
