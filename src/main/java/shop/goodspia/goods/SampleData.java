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
import shop.goodspia.goods.order.entity.OrderGoods;
import shop.goodspia.goods.order.entity.Orders;
import shop.goodspia.goods.payment.entity.Payments;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

//@Component
//@RequiredArgsConstructor
//@Profile("local")
public class SampleData {

//    private final Sample sample;
//
//    @PostConstruct
//    public void init() {
//        sample.sampleInit();
//    }
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class Sample {
//
//        private final EntityManager em;
//        private final PasswordEncoder passwordEncoder;
//
//        public void sampleInit() {
//            for (int k = 1; k <= 20; k++) {
//                Artist artist = Artist.builder()
//                        .nickname("ArtistNickname" + k)
//                        .profileImage("aaaaaaaa-ArtistProfileImage.png")
//                        .accountBank(AccountBank.KB)
//                        .accountNumber("123123-00-123123")
//                        .phoneNumber("010-0000-0000")
//                        .build();
//                em.persist(artist);
//
//                Member member = Member.builder()
//                        .nickname("MemberNickname" + k)
//                        .email("MemberEmail" + k + "@email.com")
//                        .password(passwordEncoder.encode("MemberPassword"))
//                        .artist(artist)
//                        .build();
//                em.persist(member);
//
//                for (int i = 1; i <= 100; i++) {
//                    Goods goods = Goods.builder()
//                            .name("GoodsName" + i)
//                            .thumbnail("aaaaaaaa-GoodsImage.png" + i)
//                            .category("GoodsCategory" + i)
//                            .summary("GoodsSummary" + i)
//                            .content("GoodsContent" + i)
//                            .price(10000)
//                            .artist(artist)
//                            .build();
//                    em.persist(goods);
//
//                    for (int j = 1; j <= 2; j++) {
//                        Design design = Design.builder()
//                                .designName("DesignName" + j)
//                                .goods(goods)
//                                .build();
//                        em.persist(design);
//
//                        Cart cart = Cart.builder()
//                                .quantity(10)
//                                .design(design)
//                                .goods(goods)
//                                .member(member)
//                                .build();
//                        em.persist(cart);
//                    }
//
//                    for (int l = 1; l <= 10; l++) {
//                        List<OrderGoods> orderGoodsList = new ArrayList<>();
//                        OrderGoods orderGoods1 = OrderGoods.createOrderGoods(
//                                goods, i, i * 10000, "DesignName1");
//                        em.persist(orderGoods1);
//
//                        OrderGoods orderGoods2 = OrderGoods.createOrderGoods(
//                                goods, i, i * 10000, "DesignName2");
//                        em.persist(orderGoods2);
//
//                        orderGoodsList.add(orderGoods1);
//                        orderGoodsList.add(orderGoods2);
//
//                        Orders order = Orders.createOrder(member, orderGoodsList);
//                        em.persist(order);
//                    }
//                }
//            }
//
//        }
//    }
}
