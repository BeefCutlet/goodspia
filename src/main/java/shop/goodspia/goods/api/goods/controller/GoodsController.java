package shop.goodspia.goods.api.goods.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.goodspia.goods.global.common.util.ImageUpload;
import shop.goodspia.goods.api.goods.dto.GoodsSaveRequest;
import shop.goodspia.goods.api.goods.dto.GoodsUpdateRequest;
import shop.goodspia.goods.api.goods.service.GoodsService;
import shop.goodspia.goods.global.security.dto.MemberPrincipal;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;
    private final ImageUpload imageUpload;

    @Value("${base-url}")
    private String baseUrl;

    /**
     * 굿즈 관련 정보를 저장하는 API
     * 굿즈 메인 이미지, 이름, 요약, 설명, 카테고리 저장
     */
    @PostMapping
    public ResponseEntity<?> addGoods(@RequestPart @Valid GoodsSaveRequest goods,
                                      @RequestPart MultipartFile thumbnail,
                                      @RequestPart MultipartFile contentImage,
                                      @AuthenticationPrincipal MemberPrincipal principal) {
        //세션에서 아티스트 아이디 반환
        Long memberId = principal.getId();

        //굿즈 정보(데이터, 이미지) 저장 -> DB, 스토리지
        goodsService.addGoods(memberId, goods, thumbnail, contentImage);

        return ResponseEntity.noContent().build();
    }

    /**
     * 굿즈 관련 정보 수정 API
     */
    @PutMapping
    public ResponseEntity<?> modifyDetails(@RequestPart GoodsUpdateRequest goods,
                                           @RequestPart(required = false) MultipartFile thumbnail,
                                           @RequestPart(required = false) MultipartFile contentImage,
                                           @AuthenticationPrincipal MemberPrincipal principal) {
        //굿즈 정보 수정
        goodsService.modifyGoods(principal.getId(), goods, thumbnail, contentImage);
        return ResponseEntity.noContent().build();
    }

    /**
     * 등록한 굿즈 삭제 API
     */
    @DeleteMapping("/remove/{goodsId}")
    public ResponseEntity<?> delete(@PathVariable Long goodsId) {
        goodsService.delete(goodsId);
        return ResponseEntity.noContent().build();
    }
}
