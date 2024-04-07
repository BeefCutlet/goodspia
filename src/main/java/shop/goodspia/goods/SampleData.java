package shop.goodspia.goods;

//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import shop.goodspia.goods.artist.entity.Artist;
//import shop.goodspia.goods.cart.entity.Cart;
//import shop.goodspia.goods.common.dto.AccountBank;
//import shop.goodspia.goods.goods.entity.Design;
//import shop.goodspia.goods.goods.entity.Goods;
//import shop.goodspia.goods.member.entity.Address;
//import shop.goodspia.goods.member.entity.Gender;
//import shop.goodspia.goods.member.entity.Member;
//import shop.goodspia.goods.member.entity.MemberStatus;
//import shop.goodspia.goods.order.entity.OrderGoods;
//import shop.goodspia.goods.order.entity.Orders;
//
//import javax.annotation.PostConstruct;
//import javax.persistence.EntityManager;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//@Profile("dev")
public class SampleData {
//
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
//            for (int k = 201; k <= 300; k++) {
//                Member member = Member.builder()
//                        .email("member" + k +"@email.com")
//                        .password(passwordEncoder.encode("memberpassword" + k))
//                        .nickname("닉네임" + k)
//                        .name("김땡땡")
//                        .gender((k % 2 == 0) ? Gender.MAN : Gender.WOMAN)
//                        .birthday("20000101")
//                        .phoneNumber("010-1234-1234")
//                        .address(Address.of(
//                                "01234",
//                                "서울특별시 하나시 둘로11",
//                                "하나빌라 101호"))
//                        .status(MemberStatus.ACTIVE)
//                        .build();
//                em.persist(member);
//                Artist artist = Artist.builder()
//                        .nickname("아티스트" + k)
//                        .profileImage("https://cdn.vuetifyjs.com/images/cards/sunshine.jpg")
//                        .accountBank(AccountBank.KB)
//                        .accountNumber("123123-00-123123")
//                        .phoneNumber("010-0000-0000")
//                        .build();
//                em.persist(artist);
//
//
//                for (int i = 1; i <= 20; i++) {
//                    Goods goods = Goods.builder()
//                            .name("굿즈" + (int) (Math.random() * 10000))
//                            .content("굿즈 내용입니다." + k)
//                            .price(10000)
//                            .stock(100)
//                            .thumbnail((k % 3 == 0)
//                                    ? "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
//                                    : (k % 3 == 1)
//                                    ? "https://cdn.vuetifyjs.com/images/parallax/material.jpg"
//                                    : "https://picsum.photos/500/300?image=232")
//                            .isDeleted(0)
//                            .artist(artist)
//                            .build();
//                    em.persist(goods);
//
//                    for (int j = 1; j <= 3; j++) {
//                        Design design = Design.builder()
//                                .designName("디자인" + j)
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
//                    List<OrderGoods> orderGoodsList = new ArrayList<>();
//                    OrderGoods orderGoods1 = OrderGoods.from(
//                            goods, i, i * 10000, "디자인1");
//                    em.persist(orderGoods1);
//
//                    OrderGoods orderGoods2 = OrderGoods.from(
//                            goods, i, i * 10000, "디자인2");
//                    em.persist(orderGoods2);
//
//                    orderGoodsList.add(orderGoods1);
//                    orderGoodsList.add(orderGoods2);
//
//                    Orders order = Orders.from(member, orderGoodsList);
//                    em.persist(order);
//                }
//                em.flush();
//            }
//        }
//    }
}
