package shop.goodspia.goods.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.goodspia.goods.dto.ArtistDto;
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
     * @param artistDto
     * @return
     */
    @PostMapping("/register")
    public String register(@RequestPart ArtistDto artistDto,
                           @RequestPart(required = false) MultipartFile profile,
                           HttpSession session) {
        String profileImageName = ImageUpload.uploadImage(profile);
        artistDto.setProfileImage(profileImageName);
        Long memberId = (Long) session.getAttribute("memberId");
        artistService.registerArtist(memberId, artistDto);
        return "";
    }

    /**
     * 아티스트 정보 수정 API
     * @param artistDto
     * @return 이동할 페이지 URL
     */
    @PatchMapping("/modify/{artistId}")
    public String modify(@PathVariable Long artistId,
                         @RequestPart ArtistDto artistDto,
                         @RequestPart(required = false) MultipartFile profile) {
        String profileImageName = ImageUpload.uploadImage(profile);
        artistDto.setProfileImage(profileImageName);
        artistDto.setId(artistId);
        artistService.modifyArtist(artistDto);
        return "";
    }
}
