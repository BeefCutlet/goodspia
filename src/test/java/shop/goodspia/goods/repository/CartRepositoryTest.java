package shop.goodspia.goods.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.artist.entity.Artist;
import shop.goodspia.goods.artist.repository.ArtistRepository;
import shop.goodspia.goods.cart.dto.CartResponse;
import shop.goodspia.goods.cart.entity.Cart;
import shop.goodspia.goods.cart.repository.CartQueryRepository;
import shop.goodspia.goods.cart.repository.CartRepository;
import shop.goodspia.goods.common.dto.AccountBank;
import shop.goodspia.goods.config.RepositoryTestConfiguration;
import shop.goodspia.goods.goods.entity.Design;
import shop.goodspia.goods.goods.entity.Goods;
import shop.goodspia.goods.goods.repository.DesignRepository;
import shop.goodspia.goods.goods.repository.GoodsRepository;
import shop.goodspia.goods.member.entity.Member;
import shop.goodspia.goods.member.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@Transactional
@Import(RepositoryTestConfiguration.class)
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartQueryRepository cartQueryRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private DesignRepository designRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private ArtistRepository artistRepository;

    @Test
    void findCartListSuccess() {
        Artist artist = createArtist();
        Goods goods = createGoods(artist);
        Member member = createMember(artist);
        Design design = goods.getDesigns().get(0);
        //장바구니 리스트 생성
        createCartList(10, member, goods, design, 10);

        //장바구니 리스트 조회
        PageRequest pageRequest = PageRequest.of(1, 3);
        Page<CartResponse> cartList = cartQueryRepository.findCartList(member.getId(), pageRequest);

        List<CartResponse> content = cartList.getContent();
        CartResponse cartResponse = content.get(0);
        //총 페이지 수
        int totalPages = cartList.getTotalPages();
        //페이지 당 데이터 개수
        int pageSize = cartList.getPageable().getPageSize();
        //시작 인덱스
        long offset = cartList.getPageable().getOffset();

        assertThat(cartResponse.getDesignName()).isEqualTo("DesignName");
        assertThat(cartResponse.getQuantity()).isEqualTo(17);
        assertThat(cartResponse.getPrice()).isEqualTo(10000);
        assertThat(cartResponse.getThumbnail()).isEqualTo("aaaaaaaa-GoodsThumbnail.png");
        assertThat(cartResponse.getGoodsName()).isEqualTo("GoodsName");
        assertThat(totalPages).isEqualTo(4);
        assertThat(pageSize).isEqualTo(3);
        assertThat(offset).isEqualTo(3);
    }

    @Test
    void findCartListFailed() {
        Artist artist = createArtist();
        Member member = createMember(artist);

        PageRequest pageRequest = PageRequest.of(1, 3);
        Page<CartResponse> cartList = cartQueryRepository.findCartList(member.getId(), pageRequest);

        assertThat(cartList.getContent()).isEmpty();
    }

    private void createCartList(int quantity, Member member, Goods goods, Design design, int count) {
        for (int i = 1; i <= count; i++) {
            createCart(quantity + i, member, goods, design);
        }
    }

    private Cart createCart(int quantity, Member member, Goods goods, Design design) {
        Cart cart = Cart.builder()
                .quantity(quantity)
                .member(member)
                .goods(goods)
                .design(design)
                .build();
        return cartRepository.save(cart);
    }

    private Artist createArtist() {
        Artist artist = Artist.builder()
                .nickname("ArtistNickname")
                .profileImage("aaaaaaaa-ArtistProfileImage.png")
                .accountBank(AccountBank.KB)
                .accountNumber("123123-00-123123")
                .phoneNumber("010-0000-0000")
                .build();
        return artistRepository.save(artist);
    }

    private Goods createGoods(Artist artist) {
        Goods goods = Goods.builder()
                .name("GoodsName")
                .thumbnail("aaaaaaaa-GoodsThumbnail.png")
                .category("GoodsCategory")
                .summary("GoodsSummary")
                .content("GoodsContent")
                .price(10000)
                .artist(artist)
                .build();
        Goods savedGoods = goodsRepository.save(goods);
        List<Design> designs = createDesigns(savedGoods);
        goods.addDesign(designs);
        return savedGoods;
    }

    private List<Design> createDesigns(Goods goods) {
        Design design = Design.builder()
                .designName("DesignName")
                .goods(goods)
                .build();
        designRepository.save(design);

        List<Design> designs = new ArrayList<>();
        designs.add(design);
        return designs;
    }

    private Member createMember(Artist artist) {
        Member member = Member.builder()
                .email("MemberEmail")
                .nickname("MemberNickname")
                .password("MemberPassword")
                .artist(artist)
                .build();
        return memberRepository.save(member);
    }
}
