package shop.goodspia.goods.controller.query;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.dto.Response;
import shop.goodspia.goods.dto.artist.ArtistResponse;
import shop.goodspia.goods.service.ArtistService;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistQueryController {

    private final ArtistService artistService;

    @GetMapping("/{artistId}")
    public ResponseEntity<ArtistResponse> getArtistInfo(@PathVariable Long artistId) {
        ArtistResponse artistInfo = artistService.getArtistInfo(artistId);
        return ResponseEntity.ok(artistInfo);
    }
}
