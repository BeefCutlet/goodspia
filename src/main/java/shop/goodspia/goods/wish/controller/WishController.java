package shop.goodspia.goods.wish.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.security.dto.MemberPrincipal;
import shop.goodspia.goods.wish.dto.WishSaveRequest;
import shop.goodspia.goods.wish.service.WishService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wish")
public class WishController {

    private final WishService wishService;

    /**
     * 찜하기 등록
     */
    @PostMapping
    public ResponseEntity<?> addWish(@RequestBody @Valid WishSaveRequest wishSaveRequest,
                                     @AuthenticationPrincipal MemberPrincipal principal) {
        Long memberId = principal.getId();
        wishService.addWish(memberId, wishSaveRequest.getGoodsId());
        return ResponseEntity.noContent().build();
    }

    /**
     * 찜하기 해제
     */
    @DeleteMapping("/{goodsId}")
    public ResponseEntity<?> deleteWish(@PathVariable Long goodsId,
                                        @AuthenticationPrincipal MemberPrincipal principal) {
        wishService.deleteWish(principal.getId(), goodsId);
        return ResponseEntity.noContent().build();
    }
}
