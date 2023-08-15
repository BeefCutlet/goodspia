package shop.goodspia.goods.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.dto.CartDto;
import shop.goodspia.goods.entity.Cart;
import shop.goodspia.goods.entity.Design;
import shop.goodspia.goods.entity.Goods;
import shop.goodspia.goods.entity.Member;
import shop.goodspia.goods.exception.CartNotFoundException;
import shop.goodspia.goods.exception.DesignNotFoundException;
import shop.goodspia.goods.exception.GoodsNotFoundException;
import shop.goodspia.goods.exception.MemberNotFoundException;
import shop.goodspia.goods.repository.CartRepository;
import shop.goodspia.goods.repository.DesignRepository;
import shop.goodspia.goods.repository.GoodsRepository;
import shop.goodspia.goods.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final GoodsRepository goodsRepository;
    private final DesignRepository designRepository;

    //카트에 굿즈 추가
    public Long addCart(CartDto cartDto) {
        Member member = memberRepository.findById(cartDto.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException("회원 정보가 없습니다."));

        Goods goods = goodsRepository.findById(cartDto.getGoodsId())
                .orElseThrow(() -> new GoodsNotFoundException("굿즈 정보가 없습니다."));

        Design design = designRepository.findById(cartDto.getDesignId())
                .orElseThrow(() -> new DesignNotFoundException("디자인 정보가 없습니다."));

        Cart cart = Cart.createCart(cartDto.getQuantity(), member, goods, design);
        return cartRepository.save(cart).getId();
    }

    //장바구니 개수 변경용 메서드
    public void changeQuantity(long cartId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("장바구니 굿즈 정보가 존재하지 않습니다."));
        cart.changeQuantity(quantity);
    }

    //장바구니 목록 삭제용 메서드
    public void deleteCart(long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("장바구니 굿즈 정보가 존재하지 않습니다."));
        cartRepository.delete(cart);
    }
}
