package shop.goodspia.goods.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.artist.entity.Artist;
import shop.goodspia.goods.artist.repository.ArtistRepository;
import shop.goodspia.goods.cart.entity.Cart;
import shop.goodspia.goods.cart.repository.CartQueryRepository;
import shop.goodspia.goods.cart.repository.CartRepository;
import shop.goodspia.goods.common.dto.AccountBank;
import shop.goodspia.goods.config.RepositoryTestConfiguration;
import shop.goodspia.goods.goods.dto.GoodsResponse;
import shop.goodspia.goods.goods.entity.Design;
import shop.goodspia.goods.goods.entity.Goods;
import shop.goodspia.goods.goods.repository.DesignRepository;
import shop.goodspia.goods.goods.repository.GoodsQueryRepository;
import shop.goodspia.goods.goods.repository.GoodsRepository;
import shop.goodspia.goods.member.entity.Member;
import shop.goodspia.goods.member.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DataJpaTest
@Transactional
@Import(RepositoryTestConfiguration.class)
public class GoodsRepositoryTest {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private GoodsQueryRepository goodsQueryRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private DesignRepository designRepository;
    @Autowired
    private ArtistRepository artistRepository;

    @Test
    void findGoodsListSuccess() {
        Artist artist = createArtist();
        createGoodsList(artist, 10);

        PageRequest pageRequest = PageRequest.of(1, 3);
        Page<GoodsResponse> goodsList = goodsQueryRepository.findGoodsList(pageRequest, "GoodsCategory");

        List<GoodsResponse> content = goodsList.getContent();
        GoodsResponse goodsResponse = content.get(0);
        int totalPages = goodsList.getTotalPages();
        int pageSize = goodsList.getPageable().getPageSize();
        long offset = goodsList.getPageable().getOffset();

        assertThat(goodsResponse.getGoodsName()).isEqualTo("GoodsName");
        assertThat(goodsResponse.getPrice()).isEqualTo(10000);
        assertThat(goodsResponse.getThumbnail()).isEqualTo("aaaaaaaa-GoodsThumbnail.png");
        assertThat(totalPages).isEqualTo(4);
        assertThat(pageSize).isEqualTo(3);
        assertThat(offset).isEqualTo(3);
    }

    private void createGoodsList(Artist artist, int count) {
        for (int i = 1; i <= count; i++) {
            createGoods(artist);
        }
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
