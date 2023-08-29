package shop.goodspia.goods.controller.query;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.dto.Response;
import shop.goodspia.goods.dto.artist.ArtistResponseDto;
import shop.goodspia.goods.service.ArtistService;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistQueryController {

    private final ArtistService artistService;

    @GetMapping("/{artistId}")
    public Response<ArtistResponseDto> getArtistInfo(@PathVariable long artistId) {
        ArtistResponseDto artistInfo = artistService.getArtistInfo(artistId);
        return Response.of(HttpStatus.OK.value(), "아티스트 정보 조회 성공", artistInfo);
    }
}
