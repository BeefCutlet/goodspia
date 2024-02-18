package shop.goodspia.goods.goods.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.goodspia.goods.common.util.ImageUpload;
import shop.goodspia.goods.goods.dto.GoodsSaveRequest;
import shop.goodspia.goods.goods.dto.GoodsUpdateRequest;
import shop.goodspia.goods.goods.service.GoodsService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    @Value("${base-url}")
    private String baseUrl;

    /**
     * 굿즈 관련 정보를 저장하는 API
     * 굿즈 메인 이미지, 이름, 요약, 설명, 카테고리 저장
     * @param goods 굿즈 이름, 요약, 설명, 카테고리 저장
     * @param thumbnail 굿즈 메인 이미지
     * @return 이동할 URL 반환 - 아티스트 페이지
     */
    @PostMapping
    public ResponseEntity<?> addGoods(@RequestPart @Valid GoodsSaveRequest goods,
                                      @RequestPart MultipartFile thumbnail,
                                      HttpServletRequest request) {
        //굿즈 메인 이미지 업로드 후 저장 URL 반환
        String imageUrl = ImageUpload.uploadImage(thumbnail);
        goods.setThumbnail(imageUrl);

        //세션에서 아티스트 아이디 반환
        Long artistId = (Long) request.getAttribute("artistId");
        goodsService.addGoods(artistId, goods);
        return ResponseEntity.created(URI.create(baseUrl)).build();
    }

    /**
     * 굿즈 설명에 들어가는 이미지를 저장하는 API
     * @param contentImage
     * @return 저장된 이미지 URL
     */
    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> savePicture(@RequestPart MultipartFile contentImage) {
        //이미지 업로드 후 이미지 URL 반환
        String imageUrl = ImageUpload.uploadImage(contentImage);
        return ResponseEntity.ok(imageUrl);
    }

    /**
     * 굿즈 관련 정보 수정 API
     * @param goods
     * @param thumbnail
     * @return
     */
    @PutMapping(value = "/{goodsId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> modifyDetails(@PathVariable Long goodsId,
                                           @RequestPart GoodsUpdateRequest goods,
                                           @RequestPart MultipartFile thumbnail) {
        //굿즈 썸네일 저장
        String uploadedThumbnail = ImageUpload.uploadImage(thumbnail);
        goods.setThumbnail(uploadedThumbnail);

        //굿즈 정보 수정
        goodsService.modifyGoods(goodsId, goods);
        return ResponseEntity.noContent().build();
    }

    /**
     * 등록한 굿즈 삭제 API
     * @param goodsId 삭제할 굿즈의 ID
     * @return
     */
    @PutMapping("/remove/{goodsId}")
    public ResponseEntity<?> delete(@PathVariable Long goodsId) {
        goodsService.delete(goodsId);
        return ResponseEntity.noContent().build();
    }
}
