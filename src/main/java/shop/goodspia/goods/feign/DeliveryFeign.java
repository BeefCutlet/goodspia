package shop.goodspia.goods.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.goodspia.goods.dto.sweettracker.SweetTrackerResponseDto;
import shop.goodspia.goods.dto.sweettracker.SweetTrackerTrackingResponseDto;

@FeignClient(name = "SweetTracker", url = "http://info.sweettracker.co.kr")
public interface DeliveryFeign {

    //택배사 목록 가져오기
    @GetMapping("/api/v1/companylist")
    SweetTrackerResponseDto getCompany(@RequestParam String t_key);

    //추천 택배사 목록 가져오기
    @GetMapping("/api/v1/recommend")
    SweetTrackerResponseDto getRecommendCompany(@RequestParam String t_invoice,
                                                @RequestParam String t_key);

    //운송장 번호 조회
    //t_code : 택배사 코드
    //t_invoice : 운송장 번호
    //t_key : 발급받은 키
    @GetMapping("/api/v1/trackingInfo")
    SweetTrackerTrackingResponseDto getTrackingInfo(@RequestParam String t_code,
                                                    @RequestParam String t_invoice,
                                                    @RequestParam String t_key);

}
