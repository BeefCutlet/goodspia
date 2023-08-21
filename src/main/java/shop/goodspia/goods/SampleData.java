package shop.goodspia.goods;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.entity.*;

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
                    .password("MemberPassword")
                    .artist(artist)
                    .build();
            em.persist(member);

            for (int i = 0; i < 100; i++) {
                Goods goods = Goods.builder()
                        .name("GoodsName" + i)
                        .image("GoodsImage" + i)
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
