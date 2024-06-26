package shop.goodspia.goods.api.artist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.api.artist.dto.ArtistResponse;
import shop.goodspia.goods.api.artist.service.ArtistService;
import shop.goodspia.goods.global.security.dto.MemberPrincipal;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistQueryController {

    private final ArtistService artistService;

    @GetMapping
    public ResponseEntity<ArtistResponse> getArtistInfo(@AuthenticationPrincipal MemberPrincipal principal) {
        Long memberId = principal.getId();
        ArtistResponse artistInfo = artistService.getArtistInfo(memberId);
        return ResponseEntity.ok(artistInfo);
    }
}
