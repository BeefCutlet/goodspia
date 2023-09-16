package shop.goodspia.goods;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.artist.entity.Artist;
import shop.goodspia.goods.cart.entity.Cart;
import shop.goodspia.goods.common.dto.AccountBank;
import shop.goodspia.goods.goods.entity.Design;
import shop.goodspia.goods.goods.entity.Goods;
import shop.goodspia.goods.member.entity.Member;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
@Profile("local")
public class SampleData {

    private final Sample sample;

    @PostConstruct
    public void init() {
        sample.sampleInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class Sample {

        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        public void sampleInit() {
            Artist artist = Artist.builder()
                    .nickname("ArtistNickname")
                    .profileImage("ArtistProfileImage")
                    .accountBank(AccountBank.KB)
                    .accountNumber("123123-00-123123")
                    .phoneNumber("010-0000-0000")
                    .build();
            em.persist(artist);

            Member member = Member.builder()
                    .nickname("MemberNickname")
                    .email("MemberEmail")
                    .password(passwordEncoder.encode("MemberPassword"))
                    .artist(artist)
                    .build();
            em.persist(member);

            for (int i = 0; i < 100; i++) {
                Goods goods = Goods.builder()
                        .name("GoodsName" + i)
                        .thumbnail("GoodsImage" + i)
                        .category("GoodsCategory" + i)
                        .summary("GoodsSummary" + i)
                        .content("GoodsContent" + i)
                        .price(10000)
                        .artist(artist)
                        .build();
                em.persist(goods);

                for (int j = 0; j < 3; j++) {
                    Design design = Design.builder()
                            .designName("DesignName" + i)
                            .goods(goods)
                            .build();
                    em.persist(design);

                    Cart cart = Cart.builder()
                            .quantity(10)
                            .design(design)
                            .goods(goods)
                            .member(member)
                            .build();
                    em.persist(cart);
                }
            }
        }
    }
}
