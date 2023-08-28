package shop.goodspia.goods.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.goodspia.goods.dto.artist.ArtistRequestDto;
import shop.goodspia.goods.service.ArtistService;
import shop.goodspia.goods.util.ImageUpload;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/artist")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    /**
     * 아티스트 등록 API
     * @param artistRequestDto
     * @return
     */
    @PostMapping("/register")
    public String register(@RequestPart ArtistRequestDto artistRequestDto,
                           @RequestPart(required = false) MultipartFile profile,
                           HttpSession session) {
        String profileImageName = ImageUpload.uploadImage(profile);
        artistRequestDto.setProfileImage(profileImageName);
        Long memberId = (Long) session.getAttribute("memberId");
        artistService.registerArtist(memberId, artistRequestDto);
        return "";
    }

    /**
     * 아티스트 정보 수정 API
     * @param artistRequestDto
     * @return 이동할 페이지 URL
     */
    @PatchMapping("/modify/{artistId}")
    public String modify(@PathVariable Long artistId,
                         @RequestPart ArtistRequestDto artistRequestDto,
                         @RequestPart(required = false) MultipartFile profile) {
        String profileImageName = ImageUpload.uploadImage(profile);
        artistRequestDto.setProfileImage(profileImageName);
        artistRequestDto.setId(artistId);
        artistService.modifyArtist(artistRequestDto);
        return "";
    }
}
