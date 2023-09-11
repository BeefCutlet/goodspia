package shop.goodspia.goods.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.dto.cart.CartResponse;
import shop.goodspia.goods.dto.cart.CartSaveRequest;
import shop.goodspia.goods.entity.Cart;
import shop.goodspia.goods.entity.Design;
import shop.goodspia.goods.entity.Goods;
import shop.goodspia.goods.entity.Member;
import shop.goodspia.goods.repository.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final GoodsRepository goodsRepository;
    private final DesignRepository designRepository;
    private final CartQueryRepository cartQueryRepository;

    /**
     * 카트에 굿즈 추가
     * @param cartSaveRequest
     * @return
     */
    public Long addCart(Long memberId, CartSaveRequest cartSaveRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        Goods goods = goodsRepository.findById(cartSaveRequest.getGoodsId())
                .orElseThrow(() -> new IllegalArgumentException("굿즈 정보를 찾을 수 없습니다."));

        Design design = designRepository.findById(cartSaveRequest.getDesignId())
                .orElseThrow(() -> new IllegalArgumentException("디자인 정보를 찾을 수 없습니다."));

        Cart cart = Cart.createCart(cartSaveRequest.getQuantity(), member, goods, design);
        return cartRepository.save(cart).getId();
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


    public Page<CartResponse> getCartList(Long memberId, Pageable pageable) {
        return cartQueryRepository.findCartList(memberId, pageable);
    }
}
