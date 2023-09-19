package shop.goodspia.goods.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.artist.entity.Artist;
import shop.goodspia.goods.artist.repository.ArtistRepository;
import shop.goodspia.goods.cart.dto.CartSaveRequest;
import shop.goodspia.goods.cart.entity.Cart;
import shop.goodspia.goods.cart.repository.CartQueryRepository;
import shop.goodspia.goods.cart.repository.CartRepository;
import shop.goodspia.goods.cart.service.CartService;
import shop.goodspia.goods.common.dto.AccountBank;
import shop.goodspia.goods.goods.dto.GoodsSaveRequest;
import shop.goodspia.goods.goods.entity.Design;
import shop.goodspia.goods.goods.entity.Goods;
import shop.goodspia.goods.goods.repository.DesignRepository;
import shop.goodspia.goods.goods.repository.GoodsRepository;
import shop.goodspia.goods.member.dto.MemberSaveRequest;
import shop.goodspia.goods.member.entity.Member;
import shop.goodspia.goods.member.repository.MemberRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@Slf4j
class CartServiceTest {

    private CartService cartService;
    private GoodsRepository goodsRepository = mock(GoodsRepository.class);
    private MemberRepository memberRepository = mock(MemberRepository.class);
    private ArtistRepository artistRepository = mock(ArtistRepository.class);
    private DesignRepository designRepository = mock(DesignRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private CartQueryRepository cartQueryRepository = mock(CartQueryRepository.class);

    @BeforeEach
    void beforeEach() {
        cartService = new CartService(cartRepository, memberRepository, goodsRepository, designRepository, cartQueryRepository);
    }

    @Test
    void addCart() {
//        Member member = createMember();
//        Artist artist = createArtist();
//        Goods goods = createGoods(artist);
//        Design design = createDesign("DesignName", goods);
//        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
//        when(goodsRepository.findById(1L)).thenReturn(Optional.of(goods));
//        when(designRepository.findById(1L)).thenReturn(Optional.of(design));
//
//        CartSaveRequest cartSaveRequest = new CartSaveRequest(10000, 1L, 1L);
//        Long cartId = cartService.addCart(1L, cartSaveRequest);
//        assertThat(cartId).isEqualTo(1);
    }

    private Goods createGoods(Artist artist) {
        return Goods.builder()
                .name("GoodsName")
                .thumbnail("GoodsThumbnail")
                .content("GoodsContent")
                .summary("GoodsSummary")
                .category("GoodsCategory")
                .price(10000)
                .artist(artist)
                .build();
    }

    private Member createMember() {
        return Member.builder()
                .email("MemberEmail")
                .password("MemberPassword")
                .nickname("MemberNickname")
                .isWithdraw(0)
                .build();
    }

    private Artist createArtist() {
        return Artist.builder()
                .nickname("ArtistNickname")
                .accountBank(AccountBank.KB)
                .accountNumber("123123-12-123123")
                .profileImage("aaaaaaaa-ProfileImage.png")
                .phoneNumber("010-0000-0000")
                .build();
    }

    private Design createDesign(String designName, Goods goods) {
        return Design.builder()
                .designName(designName)
                .goods(goods)
                .build();
    }
}