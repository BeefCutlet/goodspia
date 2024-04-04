package shop.goodspia.goods.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
     * 장바구니 저장
     * @param memberId, cartSaveRequest
     * @return
     */
    public Long addCart(Long memberId, CartSaveRequest cartSaveRequest) {
        log.info("MySQL Cart Save Start");
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        Goods goods = goodsRepository.findById(cartSaveRequest.getGoodsId())
                .orElseThrow(() -> new IllegalArgumentException("굿즈 정보를 찾을 수 없습니다."));

        Design design = designRepository.findById(cartSaveRequest.getDesignId())
                .orElseThrow(() -> new IllegalArgumentException("디자인 정보를 찾을 수 없습니다."));

        Cart cart = Cart.createCart(cartSaveRequest.getQuantity(), member, goods, design);
        Long savedCartId = cartRepository.save(cart).getId();
        log.info("MySQL Cart Save End");
        return savedCartId;
    }

    /**
     * 장바구니 저장 - Redis에 저장
     * @param memberId, cartSaveRequest
     * @return
     */
    public void addRedisCart(Long memberId, CartSaveRequest cartSaveRequest) {
        log.info("Redis Cart Save Start");
        memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        Goods goods = goodsRepository.findById(cartSaveRequest.getGoodsId())
                .orElseThrow(() -> new IllegalArgumentException("굿즈 정보를 찾을 수 없습니다."));

        //디자인 데이터 정합성 검사
        List<Design> designs = goods.getDesigns();
        boolean isDesignValid = false;
        for (Design design : designs) {
            if (design.getId() == cartSaveRequest.getDesignId()) {
                isDesignValid = true;
                RedisCart redisCart = RedisCart.builder()
                        .quantity(cartSaveRequest.getQuantity())
                        .goodsId(goods.getId())
                        .goodsName(goods.getName())
                        .goodsThumbnail(goods.getThumbnail())
                        .goodsPrice(goods.getPrice())
                        .designId(design.getId())
                        .designName(design.getDesignName())
                        .build();
                cartRedisRepository.save(memberId, redisCart);
                break;
            }
        }

        if (!isDesignValid) {
            throw new IllegalArgumentException("디자인 정보를 찾을 수 없습니다.");
        }
        log.info("Redis Cart Save End");
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
        return cartRedisRepository.findAllByMemberId(memberId);
    }
}
