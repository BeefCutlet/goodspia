package shop.goodspia.goods.service;

import com.siot.IamportRestClient.response.IamportResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.goodspia.goods.dto.IAmPortRequestDto;
import shop.goodspia.goods.dto.IAmPortResponseDto;
import shop.goodspia.goods.dto.PaymentDto;
import shop.goodspia.goods.dto.PaymentListDto;
import shop.goodspia.goods.entity.Goods;
import shop.goodspia.goods.entity.Member;
import shop.goodspia.goods.entity.Payment;
import shop.goodspia.goods.exception.GoodsNotFoundException;
import shop.goodspia.goods.exception.MemberNotFoundException;
import shop.goodspia.goods.feign.IAmPortFeign;
import shop.goodspia.goods.repository.GoodsRepository;
import shop.goodspia.goods.repository.MemberRepository;
import shop.goodspia.goods.repository.PaymentRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final IAmPortFeign iAmPortFeign;
    private final PaymentRepository paymentRepository;
    private final GoodsRepository goodsRepository;
    private final MemberRepository memberRepository;

    /**
     * 결제 목록에 추가
     * @param paymentDto
     * @return
     */
    public Long addPayment(PaymentDto paymentDto, long memberId) {
        log.info("addPayment Start, memberId={}", memberId);
        paymentDto.setMemberId(memberId);
        return savePayment(paymentDto);
    }

    /**
     * 결제 정보 추가
     * @param paymentListDto
     */
    public void addPaymentList(PaymentListDto paymentListDto, long memberId) {
        for (PaymentDto paymentDto : paymentListDto.getPaymentDtos()) {
            paymentDto.setMemberId(memberId);
            savePayment(paymentDto);
        }
    }

    private Long savePayment(PaymentDto paymentDto) {
        Goods goods = goodsRepository.findById(paymentDto.getGoodsId())
                .orElseThrow(() -> new GoodsNotFoundException("Goods Data Not Found"));

        Member member = memberRepository.findById(paymentDto.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException("Member Data Not Found"));

        paymentDto.setGoods(goods);
        paymentDto.setMember(member);
        Payment payment = Payment.createPayment(paymentDto);
        Payment savedPayment = paymentRepository.save(payment);
        return savedPayment.getId();
    }

    /**
     * IamPort 결제 검증용 결제 정보 사전 등록
     * @param iAmPortDto
     * @return
     */
    public IAmPortResponseDto registerPaymentInfo(IAmPortRequestDto iAmPortDto) {
        IamportResponse iamportResponse = iAmPortFeign.prepareVerify(
                iAmPortDto.getMerchant_uid(),
                iAmPortDto.getAmount());

        return (IAmPortResponseDto) iamportResponse.getResponse();
    }

    public IAmPortResponseDto verifyPaymentInfo(String merchantUid, IAmPortRequestDto iAmPortRequestDto) {
        return iAmPortFeign.searchPreparePayment(merchantUid).getResponse();
    }
}
