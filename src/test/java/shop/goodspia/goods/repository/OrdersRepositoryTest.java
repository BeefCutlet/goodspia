package shop.goodspia.goods.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.artist.entity.Artist;
import shop.goodspia.goods.artist.repository.ArtistRepository;
import shop.goodspia.goods.common.dto.AccountBank;
import shop.goodspia.goods.config.RepositoryTestConfiguration;
import shop.goodspia.goods.goods.entity.Design;
import shop.goodspia.goods.goods.entity.Goods;
import shop.goodspia.goods.goods.repository.DesignRepository;
import shop.goodspia.goods.goods.repository.GoodsRepository;
import shop.goodspia.goods.member.entity.Member;
import shop.goodspia.goods.member.repository.MemberRepository;
import shop.goodspia.goods.order.dto.OrderStatus;
import shop.goodspia.goods.order.entity.OrderGoods;
import shop.goodspia.goods.order.entity.Orders;
import shop.goodspia.goods.order.repository.OrderGoodsRepository;
import shop.goodspia.goods.order.repository.OrderQueryRepository;
import shop.goodspia.goods.order.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@Transactional
@Import(RepositoryTestConfiguration.class)
public class OrdersRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderQueryRepository orderQueryRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private DesignRepository designRepository;
    @Autowired
    private OrderGoodsRepository orderGoodsRepository;

    @DisplayName("주문 UID로 조회 성공")
    @Test
    void findOrderByUidSuccess() {
        Artist artist = createArtist();
        Goods goods = createGoods(artist);
        Orders order = createOrder(goods, artist, goods.getDesigns().get(0).getDesignName());

        Orders findOrder = orderRepository.findByOrderUid(order.getOrderUid());

        assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.READY);
        assertThat(findOrder.getMember().getEmail()).isEqualTo("MemberEmail");
        assertThat(findOrder.getMember().getNickname()).isEqualTo("MemberNickname");
        assertThat(findOrder.getMember().getPassword()).isEqualTo("MemberPassword");
        assertThat(findOrder.getMember().getIsWithdraw()).isEqualTo(0);
        assertThat(findOrder.getOrderGoods().get(0).getQuantity()).isEqualTo(10);
        assertThat(findOrder.getOrderGoods().get(0).getTotalPrice()).isEqualTo(100000);
        assertThat(findOrder.getOrderGoods().get(0).getGoodsDesign()).isEqualTo("DesignName");
    }

    @DisplayName("주문한 총 금액 조회")
    @Test
    void findTotalPrice() {
        Artist artist = createArtist();
        Goods goods = createGoods(artist);
        Orders orders = createOrder(goods, artist, goods.getDesigns().get(0).getDesignName());
        log.info("designName={}", goods.getDesigns().get(0).getDesignName());

        log.info("orderUid={}", orders.getOrderUid());
        Integer totalPrice = orderQueryRepository.findTotalPrice(orders.getOrderUid());
        log.info("totalPrice={}", totalPrice);

        assertThat(totalPrice).isEqualTo(200000);
    }

    private Orders createOrder(Goods goods, Artist artist, String design) {
        OrderGoods orderGoods1 = OrderGoods.createOrderGoods(goods, 10, 100000, design);
        OrderGoods orderGoods2 = OrderGoods.createOrderGoods(goods, 10, 100000, design);
        OrderGoods savedOrderGoods1 = orderGoodsRepository.save(orderGoods1);
        OrderGoods savedOrderGoods2 = orderGoodsRepository.save(orderGoods2);

        List<OrderGoods> orderGoodsList = new ArrayList<>();
        orderGoodsList.add(savedOrderGoods1);
        orderGoodsList.add(savedOrderGoods2);

        Member member = createMember(artist);
        memberRepository.save(member);

        Orders order = Orders.createOrder(member, orderGoodsList);
        Orders savedOrder = orderRepository.save(order);
        return savedOrder;
    }

    private Goods createGoods(Artist artist) {
        Goods goods = Goods.builder()
                .name("GoodsName")
                .thumbnail("GoodsImage")
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

    private Artist createArtist() {
        Artist artist = Artist.builder()
                .nickname("ArtistNickname")
                .profileImage("ArtistProfileImage")
                .accountBank(AccountBank.KB)
                .accountNumber("123123-00-123123")
                .phoneNumber("010-0000-0000")
                .build();
        return artistRepository.save(artist);
    }
}
