package shop.goodspia.goods.artist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.goodspia.goods.artist.dto.ArtistResponse;
import shop.goodspia.goods.artist.service.ArtistService;

@Tag(name = "아티스트 정보 조회 API", description = "아티스트 정보 조회 API")
@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistQueryController {

    private final ArtistService artistService;

    @Operation(summary = "아티스트 단건 조회 API", description = "아티스트 번호로 아티스트 단건 정보를 조회합니다.")
    @GetMapping("/{artistId}")
    public ResponseEntity<ArtistResponse> getArtistInfo(@Parameter(name = "아티스트 번호") @PathVariable Long artistId) {
        ArtistResponse artistInfo = artistService.getArtistInfo(artistId);
        return ResponseEntity.ok(artistInfo);
    }
}
