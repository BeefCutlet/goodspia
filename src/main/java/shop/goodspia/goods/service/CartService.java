package shop.goodspia.goods.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.dto.CartDto;
import shop.goodspia.goods.dto.CartResponseDto;
import shop.goodspia.goods.entity.Cart;
import shop.goodspia.goods.entity.Design;
import shop.goodspia.goods.entity.Goods;
import shop.goodspia.goods.entity.Member;
import shop.goodspia.goods.exception.CartNotFoundException;
import shop.goodspia.goods.exception.DesignNotFoundException;
import shop.goodspia.goods.exception.GoodsNotFoundException;
import shop.goodspia.goods.exception.MemberNotFoundException;
import shop.goodspia.goods.repository.*;

import java.util.List;

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
     * @param cartDto
     * @return
     */
    public Long addCart(CartDto cartDto) {
        Member member = memberRepository.findById(cartDto.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException("Member Data Not Found"));

        Goods goods = goodsRepository.findById(cartDto.getGoodsId())
                .orElseThrow(() -> new GoodsNotFoundException("Goods Data Not Found"));

        Design design = designRepository.findById(cartDto.getDesignId())
                .orElseThrow(() -> new DesignNotFoundException("Design Data Not Found"));

        Cart cart = Cart.createCart(cartDto.getQuantity(), member, goods, design);
        return cartRepository.save(cart).getId();
    }

    /**
     * 장바구니 개수 변경용 메서드
     */
    public void changeQuantity(long cartId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart Data Not Found"));
        cart.changeQuantity(quantity);
    }

    /**
     * 장바구니 목록 삭제용 메서드
     */
    public void deleteCart(long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart Data Not Found"));
        cartRepository.delete(cart);
    }


    public List<CartResponseDto> getCartList(long memberId) {
        return cartQueryRepository.findCartList(memberId);
    }
}
