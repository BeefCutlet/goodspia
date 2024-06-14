package shop.goodspia.goods.api.wish.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.api.goods.repository.GoodsRepository;
import shop.goodspia.goods.api.wish.dto.CheckWishResponse;
import shop.goodspia.goods.api.wish.repository.WishQueryRepository;
import shop.goodspia.goods.api.goods.entity.Goods;
import shop.goodspia.goods.api.member.entity.Member;
import shop.goodspia.goods.api.member.repository.MemberRepository;
import shop.goodspia.goods.api.wish.dto.WishListResponse;
import shop.goodspia.goods.api.wish.dto.WishResponse;
import shop.goodspia.goods.api.wish.entity.Wish;
import shop.goodspia.goods.api.wish.repository.WishRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WishService {

    private final WishRepository wishRepository;
    private final WishQueryRepository wishQueryRepository;
    private final GoodsRepository goodsRepository;
    private final MemberRepository memberRepository;

    /**
     * DB에 Wish 정보 저장
     */
    public void addWish(Long memberId, Long goodsId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new IllegalArgumentException("회원 정보가 존재하지 않습니다.");
        });

        Goods goods = goodsRepository.findById(goodsId).orElseThrow(() -> {
            throw new IllegalArgumentException("굿즈 정보가 존재하지 않습니다.");
        });

        Wish foundWish = wishQueryRepository.findWish(memberId, goodsId);
        if (foundWish != null) {
            throw new IllegalStateException("이미 찜하기 목록에 등록되어 있습니다.");
        }

        Wish wish = Wish.from(member, goods);
        wishRepository.save(wish);
        goods.increaseWishCount();
    }

    /**
     * DB에서 Wish 정보 삭제
     */
    public void deleteWish(Long memberId, Long goodsId) {
        Goods goods = goodsRepository.findById(goodsId).orElseThrow(() -> {
            throw new IllegalArgumentException("굿즈 정보가 존재하지 않습니다.");
        });

        Wish wish = wishQueryRepository.findWish(memberId, goodsId);
        if (wish == null) {
            throw new IllegalArgumentException("찜목록에 존재하지 않습니다.");
        }

        wishRepository.delete(wish);
        goods.decreaseWishCount();
        if (!goods.validateWishCount()) {
            throw new IllegalStateException("찜하기 수는 0 미만이 될 수 없습니다.");
        }
    }

    /**
     * 찜하기 정보 단건 조회
     */
    public CheckWishResponse getWishStatus(Long memberId, Long goodsId) {
        if (memberId == null) {
            return new CheckWishResponse(false);
        }

        Wish wish = wishQueryRepository.findWish(memberId, goodsId);
        if (wish == null) {
            return new CheckWishResponse(false);
        }

        return new CheckWishResponse(true);
    }

    /**
     * 사용자가 찜한 굿즈 목록 조회
     */
    public WishListResponse getWishList(Long memberId) {
        List<WishResponse> wishList = wishQueryRepository.getWishList(memberId);
        return WishListResponse.from(wishList);
    }
}
