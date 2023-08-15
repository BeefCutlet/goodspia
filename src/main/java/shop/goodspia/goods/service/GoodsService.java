package shop.goodspia.goods.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.dto.GoodsDto;
import shop.goodspia.goods.entity.Goods;
import shop.goodspia.goods.entity.Member;
import shop.goodspia.goods.exception.GoodsNotFoundException;
import shop.goodspia.goods.exception.MemberNotFoundException;
import shop.goodspia.goods.repository.GoodsRepository;
import shop.goodspia.goods.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GoodsService {

    private final MemberRepository memberRepository;
    private final GoodsRepository goodsRepository;

    /**
     * 굿즈 등록용 메서드
     * @param artistId
     * @param goodsDto
     * @return
     */
    public Long addGoods(long artistId, GoodsDto goodsDto) {
        //회원 조회 - 아티스트 등록 여부 파악용
        Optional<Member> findMember = memberRepository.findById(artistId);
        return findMember.map(
                member -> {
                    Goods goods = Goods.createGoods(goodsDto, member.getArtist());
                    return goodsRepository.save(goods).getId();
                }
        ).orElseThrow(() -> new MemberNotFoundException("회원이 존재하지 않습니다."));
    }

    /**
     * 굿즈 정보 수정용 메서드
     * @param goodsDto
     */
    public void modifyGoods(GoodsDto goodsDto) {
        Optional.ofNullable(goodsRepository.findById(goodsDto.getId()))
                .ifPresentOrElse(
                        goods -> goods.get().updateGoods(goodsDto),
                        () -> new GoodsNotFoundException("굿즈 정보가 없습니다.")
                );
    }

    /**
     * 굿즈 삭제 메서드 - 상태 변경
     */
    public void delete(long goodsId) {
        Optional.ofNullable(goodsRepository.findById(goodsId))
                .ifPresentOrElse(
                        goods -> goods.get().delete(),
                        () -> new GoodsNotFoundException("굿즈 정보가 없습니다.")
                );
    }
}
