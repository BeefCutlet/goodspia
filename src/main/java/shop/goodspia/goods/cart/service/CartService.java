package shop.goodspia.goods.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.cart.dto.CartResponse;
import shop.goodspia.goods.cart.dto.CartSaveListRequest;
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
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartRedisRepository cartRedisRepository;
    private final MemberRepository memberRepository;
    private final GoodsRepository goodsRepository;
    private final DesignRepository designRepository;
    private final CartQueryRepository cartQueryRepository;

    /**
     * 장바구니 저장
     */
    public void addCarts(Long memberId, CartSaveListRequest cartSaveRequest) {
        //회원 정보 존재 여부 확인
        Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        //선택된 장바구니 목록을 DB에 저장
        cartSaveRequest.getCartList().forEach((cart) -> {
            //굿즈 정보 존재 여부 확인
            Goods goods = goodsRepository.findById(cart.getGoodsId())
                    .orElseThrow(() -> new IllegalArgumentException("굿즈 정보를 찾을 수 없습니다."));

            //디자인 정보 존재 여부 확인
            Design design = designRepository.findById(cart.getDesignId())
                    .orElseThrow(() -> new IllegalArgumentException("디자인 정보를 찾을 수 없습니다."));

            //선택한 디자인이 이미 장바구니에 있는지 확인
            Optional<Cart> findCart = cartRepository.findAddedCart(member.getId(), goods.getId(), design.getId());
            if (findCart.isPresent()) {
                //이미 존재하면 수량만 추가
                int currentQuantity = findCart.get().getQuantity();
                findCart.get().changeQuantity(currentQuantity + cart.getQuantity());
            } else {
                //존재하지 않으면 새로 추가
                Cart saveCart = Cart.from(cart.getQuantity(), member, goods, design);
                cartRepository.save(saveCart);
            }
        });
    }

    /**
     * 장바구니 저장 - Redis에 저장
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
        cartRepository.deleteById(cart.getId());
    }

    /**
     * 장바구니 목록 페이징 조회
     */
    public List<CartResponse> getCartList(Long memberId) {
        return cartQueryRepository.findCartList(memberId);
    }

    /**
     * Redis에서 특정 회원의 장바구니 목록 조회
     */
    public List<RedisCart> getRedisCartList(Long memberId) {
        return cartRedisRepository.findAllByMemberId(memberId);
    }
}
