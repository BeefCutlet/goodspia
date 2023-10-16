package shop.goodspia.goods.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.cart.dto.CartResponse;
import shop.goodspia.goods.cart.dto.CartSaveRequest;
import shop.goodspia.goods.cart.entity.Cart;
import shop.goodspia.goods.cart.entity.RedisCart;
import shop.goodspia.goods.cart.repository.CartQueryRepository;
import shop.goodspia.goods.cart.repository.CartRedisRepository;
import shop.goodspia.goods.cart.repository.CartRepository;
import shop.goodspia.goods.goods.entity.Design;
import shop.goodspia.goods.goods.entity.Goods;
import shop.goodspia.goods.goods.repository.DesignRepository;
import shop.goodspia.goods.goods.repository.GoodsRepository;
import shop.goodspia.goods.member.entity.Member;
import shop.goodspia.goods.member.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartRedisRepository cartRedisRepository;
    private final MemberRepository memberRepository;
    private final GoodsRepository goodsRepository;
    private final DesignRepository designRepository;
    private final CartQueryRepository cartQueryRepository;

    /**
     * 카트에 굿즈 추가
     * @param cartSaveRequest
     * @return
     */
    public void addCart(Long memberId, CartSaveRequest cartSaveRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        Goods goods = goodsRepository.findById(cartSaveRequest.getGoodsId())
                .orElseThrow(() -> new IllegalArgumentException("굿즈 정보를 찾을 수 없습니다."));

        Design design = designRepository.findById(cartSaveRequest.getDesignId())
                .orElseThrow(() -> new IllegalArgumentException("디자인 정보를 찾을 수 없습니다."));

        RedisCart redisCart = RedisCart.builder()
                .quantity(cartSaveRequest.getQuantity())
                .memberId(memberId)
                .goodsId(cartSaveRequest.getGoodsId())
                .designId(cartSaveRequest.getDesignId())
                .build();
        cartRedisRepository.save(redisCart);
//        Cart cart = Cart.createCart(cartSaveRequest.getQuantity(), member, goods, design);
//        return cartRepository.save(cart).getId();
    }

    /**
     * 장바구니 개수 변경용 메서드
     */
    public void changeQuantity(Long cartId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 정보를 찾을 수 없습니다."));
        cart.changeQuantity(quantity);
    }

    /**
     * 장바구니 목록 삭제용 메서드
     */
    public void deleteCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 정보를 찾을 수 없습니다."));
        cartRepository.delete(cart);
    }


    /**
     * 장바구니 목록 페이징 조회
     * @param memberId
     * @param pageable
     * @return
     */
    public Page<CartResponse> getCartList(Long memberId, Pageable pageable) {
        return cartQueryRepository.findCartList(memberId, pageable);
    }

    /**
     * Redis에서 특정 회원의 장바구니 목록 조회
     * @param memberId
     * @return
     */
    public List<RedisCart> getRedisCartList(Long memberId) {
        return cartRedisRepository.findListByMemberId(memberId);
    }
}
