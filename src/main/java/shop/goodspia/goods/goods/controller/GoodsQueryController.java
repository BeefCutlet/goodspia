package shop.goodspia.goods.goods.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.goodspia.goods.goods.dto.GoodsDetailResponse;
import shop.goodspia.goods.goods.dto.GoodsListResponse;
import shop.goodspia.goods.goods.dto.GoodsResponse;
import shop.goodspia.goods.goods.service.GoodsService;

import java.util.List;

@Tag(name = "굿즈 조회 API", description = "굿즈 목록/단건 조회 API")
@Slf4j
@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsQueryController {

    private final GoodsService goodsService;

    /**
     * 최신 굿즈 리스트 - 메인페이지
     * @param category
     * @param pageable
     * @return
     */
    @Operation(summary = "굿즈 목록 조회 API", description = "굿즈 카테고리와 page와 size를 파라미터로 설정할 수 있습니다.")
    @GetMapping("/list")
    public ResponseEntity<GoodsListResponse> getGoodsList(@Parameter(name = "굿즈 카테고리", description = "굿즈 검색 시 카테고리를 선택했다면 설정")
                                                            @RequestParam(required = false) String category,
                                                          @Parameter(hidden = true) Pageable pageable) {
        log.info("전체 굿즈 리스트 조회, pageSize={}, pageNum={}", pageable.getPageSize(), pageable.getPageNumber());
        GoodsListResponse goodsList = goodsService.getGoodsList(pageable, category);
        return ResponseEntity.ok(goodsList);
    }

    /**
     * 굿즈 상세 정보 조회 - 굿즈 상세페이지
     * @param goodsId
     * @return
     */
    @Operation(summary = "굿즈 상세 정보 조회 API", description = "조회할 굿즈의 번호를 파라미터로 설정합니다.")
    @GetMapping("/detail/{goodsId}")
    public ResponseEntity<GoodsDetailResponse> getOneGoods(@Parameter(name = "굿즈 번호") @PathVariable Long goodsId) {
        log.info("굿즈 상세 정보 조회, goodsId={}", goodsId);
        GoodsDetailResponse goods = goodsService.getGoods(goodsId);
        return ResponseEntity.ok(goods);
    }

    /**
     * 아티스트가 제작한 굿즈 리스트 - 아티스트 페이지
     * @param artistId
     * @param pageable
     * @return
     */
    @Operation(summary = "아티스트가 제작한 굿즈 목록 조회 API",
            description = "조회할 아티스트의 번호를 파라미터로 설정합니다. page와 size를 파라미터로 설정할 수 있습니다.")
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<Page<GoodsResponse>> getArtistGoodsList(@Parameter(name = "아티스트 번호") @PathVariable Long artistId,
                                                                  @Parameter(hidden = true) Pageable pageable) {
        log.info("아티스트가 제작한 굿즈 리스트 조회, artistId={}", artistId);
        Page<GoodsResponse> artistGoodsList = goodsService.getArtistGoodsList(pageable, artistId);
        return ResponseEntity.ok(artistGoodsList);
    }
}
