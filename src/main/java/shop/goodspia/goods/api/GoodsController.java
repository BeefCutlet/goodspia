package shop.goodspia.goods.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.goodspia.goods.dto.GoodsDto;
import shop.goodspia.goods.security.dto.SessionUser;
import shop.goodspia.goods.service.GoodsService;
import shop.goodspia.goods.util.ImageUpload;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    /**
     * 굿즈 관련 정보를 저장하는 API
     * 굿즈 메인 이미지, 이름, 요약, 설명, 카테고리 저장
     * @param goodsDto 굿즈 이름, 요약, 설명, 카테고리 저장
     * @param mainImage 굿즈 메인 이미지
     * @return 이동할 URL 반환 - 아티스트 페이지
     */
    @PostMapping("/add/{artistId}")
    public String addGoods(@PathVariable long artistId,
                           @RequestPart GoodsDto goodsDto,
                           @RequestPart MultipartFile mainImage,
                           HttpSession session) {
        //굿즈 메인 이미지 업로드 후 저장 URL 반환
        String imageUrl = ImageUpload.uploadImage(mainImage);
        goodsDto.setImage(imageUrl);

        //세션에서 아티스트 아이디 반환
        Long id = ((SessionUser) session.getAttribute("sessionUser")).getArtistId();
        goodsService.addGoods(id, goodsDto);
        return "";
    }

    /**
     * 굿즈 설명에 들어가는 이미지를 저장하는 API
     * @param detailImage
     * @return 저장된 이미지 URL
     */
    @PostMapping("/save/image")
    public String savePicture(@RequestPart MultipartFile detailImage) {
        //이미지 업로드 후 이미지 URL 반환
        return ImageUpload.uploadImage(detailImage);
    }

    /**
     * 굿즈 관련 정보 수정 API
     * @param goodsDto
     * @param mainImage
     * @return
     */
    @PatchMapping("/modify/{artistId}/{goodsId}")
    public String modifyDetails(@PathVariable long artistId,
                                @PathVariable long goodsId,
                                @RequestPart GoodsDto goodsDto,
                                @RequestPart MultipartFile mainImage) {
        String image = ImageUpload.uploadImage(mainImage);
        goodsDto.setImage(image);
        goodsDto.setId(goodsId);
        goodsService.modifyGoods(goodsDto);
        return "";
    }

    /**
     * 등록한 굿즈 삭제 API
     * @param goodsId 삭제할 굿즈의 ID
     * @return
     */
    @PatchMapping("/delete/{goodsId}")
    public String delete(@PathVariable long goodsId) {
        goodsService.delete(goodsId);
        return "";
    }
}