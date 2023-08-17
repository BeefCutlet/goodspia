package shop.goodspia.goods.api.query;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.dto.ArtistResponseDto;
import shop.goodspia.goods.service.ArtistService;

@RestController
@RequestMapping("/artist")
@RequiredArgsConstructor
public class ArtistQueryController {

    private final ArtistService artistService;

    @GetMapping("/{artistId}")
    public ArtistResponseDto getArtistInfo(@PathVariable long artistId) {
        return artistService.getArtistInfo(artistId);
    }
}
