package shop.goodspia.goods.artist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.goodspia.goods.artist.dto.ArtistSaveRequest;
import shop.goodspia.goods.artist.dto.ArtistUpdateRequest;
import shop.goodspia.goods.artist.service.ArtistService;
import shop.goodspia.goods.common.util.ImageUpload;
import shop.goodspia.goods.security.dto.MemberPrincipal;

import javax.validation.Valid;

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
     */
    @PostMapping
    public ResponseEntity<?> registerArtist(@RequestBody @Valid ArtistSaveRequest artist,
                                            @AuthenticationPrincipal MemberPrincipal principal) {
        //현재 로그인 중인 회원 확인
        Long memberId = principal.getId();

        //현재 로그인 중인 회원을 아티스트로 등록
        artistService.registerArtist(memberId, artist);
        return ResponseEntity.noContent().build();
    }

    /**
     * 아티스트 정보 수정 API
     */
    @PutMapping
    public ResponseEntity<?> modifyArtist(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                          @RequestPart @Valid ArtistUpdateRequest artist,
                                          @RequestPart(required = false) MultipartFile profile) {
        //아티스트의 프로필 이미지 저장(갱신)
        if (!profile.isEmpty()) {
            String profileImageName = ImageUpload.uploadImage(profile);
            artist.setProfileImage(profileImageName);
        }

        //아티스트의 정보 수정
        artistService.modifyArtist(memberPrincipal.getId(), artist);
        return ResponseEntity.noContent().build();
    }
}
