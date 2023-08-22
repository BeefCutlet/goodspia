package shop.goodspia.goods.feign;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.goodspia.goods.dto.IAmPortResponseDto;

@FeignClient(name = "IAmPortOpenFeign", url = IamportClient.API_URL)
public interface IAmPortFeign {

    @PostMapping("/payments/prepare")
    IamportResponse<IAmPortResponseDto> prepareVerify(
            @RequestParam String merchant_uid,
            @RequestParam int amount
    );

    @GetMapping("/payments/prepare/{merchant_uid}")
    IamportResponse<IAmPortResponseDto> searchPreparePayment(@PathVariable String merchant_uid);
}
