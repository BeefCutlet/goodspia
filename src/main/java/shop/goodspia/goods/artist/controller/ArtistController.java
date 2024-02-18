package shop.goodspia.goods.artist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.goodspia.goods.artist.dto.ArtistSaveRequest;
import shop.goodspia.goods.artist.dto.ArtistUpdateRequest;
import shop.goodspia.goods.artist.service.ArtistService;
import shop.goodspia.goods.common.util.ImageUpload;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

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
     *
     * @param artist
     * @return
     */
    @PostMapping
    public ResponseEntity<?> register(@RequestPart @Valid ArtistSaveRequest artist,
                                      @RequestPart(required = false) MultipartFile profile,
                                      HttpServletRequest request) {
        //아티스트의 프로필 이미지 저장
        if (profile != null) {
            String profileImageName = ImageUpload.uploadImage(profile);
            artist.setProfileImage(profileImageName);
        }

        //현재 로그인 중인 회원 확인
        Long memberId = (Long) request.getAttribute("memberId");

        //현재 로그인 중인 회원을 아티스트로 등록
        Long artistId = artistService.registerArtist(memberId, artist);
        return ResponseEntity.created(URI.create(baseUrl + "/artists/" + artistId)).build();
    }

    /**
     * 아티스트 정보 수정 API
     *
     * @param artist
     * @return 이동할 페이지 URL
     */
    @PutMapping(value = "/{artistId}")
    public ResponseEntity<?> modify(@PathVariable Long artistId,
                                    @RequestPart @Valid ArtistUpdateRequest artist,
                                    @RequestPart(required = false) MultipartFile profile) {
        //아티스트의 프로필 이미지 저장(갱신)
        String profileImageName = ImageUpload.uploadImage(profile);
        artist.setProfileImage(profileImageName);

        //아티스트의 정보 수정
        artistService.modifyArtist(artistId, artist);
        return ResponseEntity.created(URI.create(baseUrl + "/artists/" + artistId)).build();
    }
}
