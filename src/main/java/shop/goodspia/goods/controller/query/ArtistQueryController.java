package shop.goodspia.goods.controller.query;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.dto.artist.ArtistResponseDto;
import shop.goodspia.goods.service.ArtistService;
import shop.goodspia.goods.service.GoodsService;

@RestController
@RequestMapping("/artist")
@RequiredArgsConstructor
public class ArtistQueryController {

    private final ArtistService artistService;
    private final GoodsService goodsService;

    @GetMapping("/{artistId}")
    public ResponseEntity<ArtistResponseDto> getArtistInfo(@PathVariable long artistId) {
        ArtistResponseDto artistInfo = artistService.getArtistInfo(artistId);
        return ResponseEntity.ok(artistInfo);
    }
}
