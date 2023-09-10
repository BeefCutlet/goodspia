package shop.goodspia.goods.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.goodspia.goods.dto.goods.GoodsSaveRequest;
import shop.goodspia.goods.dto.goods.GoodsUpdateRequest;
import shop.goodspia.goods.service.GoodsService;
import shop.goodspia.goods.util.ImageUpload;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "굿즈 등록/수정/삭제 API", description = "새로운 굿즈를 등록, 등록된 굿즈 정보를 수정, 등록했던 굿즈를 삭제하여 보이지 않도록 수정하는 API")
@Slf4j
@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    /**
     * 굿즈 관련 정보를 저장하는 API
     * 굿즈 메인 이미지, 이름, 요약, 설명, 카테고리 저장
     * @param goods 굿즈 이름, 요약, 설명, 카테고리 저장
     * @param thumbnail 굿즈 메인 이미지
     * @return 이동할 URL 반환 - 아티스트 페이지
     */
    @Operation(summary = "굿즈 등록 API", description = "새로운 굿즈를 등록하는 API")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addGoods(@Parameter(name = "굿즈 정보", description = "등록할 굿즈 정보") @RequestPart @Valid GoodsSaveRequest goods,
                           @Parameter(name = "썸네일 이미지", description = "등록할 굿즈 썸네일 이미지") @RequestPart MultipartFile thumbnail,
                           @Parameter(hidden = true) HttpServletRequest request) {
        //굿즈 메인 이미지 업로드 후 저장 URL 반환
        String imageUrl = ImageUpload.uploadImage(thumbnail);
        goods.setThumbnail(imageUrl);

        //세션에서 아티스트 아이디 반환
        Long artistId = (Long) request.getAttribute("artistId");
        goodsService.addGoods(artistId, goods);
        return "";
    }

    /**
     * 굿즈 설명에 들어가는 이미지를 저장하는 API
     * @param contentImage
     * @return 저장된 이미지 URL
     */
    @Operation(summary = "굿즈 이미지 저장 API", description = "굿즈 내용에 들어가는 이미지를 저장하는 API")
    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String savePicture(@Parameter(name = "굿즈 내용 이미지", description = "굿즈 내용에 들어가는 이미지") @RequestPart MultipartFile contentImage) {
        //이미지 업로드 후 이미지 URL 반환
        return ImageUpload.uploadImage(contentImage);
    }

    /**
     * 굿즈 관련 정보 수정 API
     * @param goods
     * @param thumbnail
     * @return
     */
    @Operation(summary = "굿즈 수정 API", description = "기존에 등록한 굿즈의 정보를 수정하는 API")
    @PatchMapping(value = "/{goodsId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String modifyDetails(@Parameter(description = "수정할 굿즈의 번호") @PathVariable Long goodsId,
                                @Parameter(name = "굿즈 정보", description = "수정할 굿즈 정보") @RequestPart GoodsUpdateRequest goods,
                                @Parameter(name = "굿즈 썸네일 이미지", description = "굿즈의 수정된 썸네일 이미지") @RequestPart MultipartFile thumbnail) {
        //굿즈 썸네일 저장
        String uploadedThumbnail = ImageUpload.uploadImage(thumbnail);
        goods.setThumbnail(uploadedThumbnail);
        goods.setId(goodsId);

        //굿즈 정보 수정
        goodsService.modifyGoods(goods);
        return "";
    }

    /**
     * 등록한 굿즈 삭제 API
     * @param goodsId 삭제할 굿즈의 ID
     * @return
     */
    @Operation(summary = "굿즈 삭제 API", description = "등록한 굿즈를 삭제 처리하는 API")
    @PatchMapping("/remove/{goodsId}")
    public String delete(@Parameter(description = "삭제 처리할 굿즈의 번호") @PathVariable Long goodsId) {
        goodsService.delete(goodsId);
        return "";
    }
}
