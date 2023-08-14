package shop.goodspia.goods.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.dto.GoodsDto;
import shop.goodspia.goods.entity.Goods;
import shop.goodspia.goods.entity.Member;
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

    public Long addGoods(GoodsDto goodsDto, String email) {
        //회원 조회 - 아티스트 등록 여부 파악용
        Optional<Member> findMember = memberRepository.findByEmail(email);
        return findMember.map(
                member -> {
                    Goods goods = Goods.createGoods(goodsDto, member.getArtist());
                    Goods savedGoods = goodsRepository.save(goods);
                    return savedGoods.getId();
                }
        ).orElseThrow(() -> new MemberNotFoundException("회원이 존재하지 않습니다."));
    }
}
