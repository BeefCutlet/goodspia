package shop.goodspia.goods.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.goodspia.goods.dto.artist.ArtistSaveRequest;
import shop.goodspia.goods.dto.artist.ArtistUpdateRequest;
import shop.goodspia.goods.service.ArtistService;
import shop.goodspia.goods.util.ImageUpload;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@Tag(name = "아티스트 등록/수정 API", description = "새로운 아티스트를 등록하거나 기존 아티스트의 정보를 수정하는 API")
@Slf4j
@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @Value("${base-url}")
    private String baseUrl;

    /**
     * 아티스트 등록 API
     * @param artist
     * @return
     */
    @Operation(summary = "아티스트 등록 API", description = "새로운 아티스트 정보를 저장하는 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "등록 후 생성될 자원의 URL", content = @Content(schema = @Schema(implementation = String.class)))
    )
    @PostMapping
    public ResponseEntity<?> register(@Parameter(name = "아티스트 정보", required = true)
                                      @RequestPart @Valid ArtistSaveRequest artist,
                                      @Parameter(name = "프로필 이미지")
                                      @RequestPart(required = false) MultipartFile profile,
                                      @Parameter(hidden = true) HttpServletRequest request) {
        //아티스트의 프로필 이미지 저장
        if (profile != null) {
            String profileImageName = ImageUpload.uploadImage(profile);
            artist.setProfileImage(profileImageName);
        }

        //현재 로그인 중인 회원 확인
        Long memberId = (Long) request.getAttribute("memberId");

        //현재 로그인 중인 회원을 아티스트로 등록
        artistService.registerArtist(memberId, artist);
        return ResponseEntity.created(URI.create(baseUrl)).build();
    }

    /**
     * 아티스트 정보 수정 API
     * @param artist
     * @return 이동할 페이지 URL
     */
    @Operation(summary = "아티스트 정보 수정 API", description = "현재 아티스트의 정보를 수정하는 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "아티스트 정보 수정 후 자원의 URL")
    )
    @PutMapping(value = "/{artistId}")
    public ResponseEntity<?> modify(@Parameter(name = "아티스트 번호", description = "정보를 수정할 아티스트의 번호") @PathVariable Long artistId,
                         @Parameter(name = "아티스트 정보", description = "수정할 아티스트의 정보") @RequestPart @Valid ArtistUpdateRequest artist,
                         @Parameter(name = "프로필 이미지", description = "수정할 아티스트의 프로필 이미지") @RequestPart(required = false) MultipartFile profile) {
        //아티스트의 프로필 이미지 저장(갱신)
        String profileImageName = ImageUpload.uploadImage(profile);
        artist.setProfileImage(profileImageName);

        //아티스트의 정보 수정
        artistService.modifyArtist(artistId, artist);
        return ResponseEntity.created(URI.create(baseUrl)).build();
    }
}
