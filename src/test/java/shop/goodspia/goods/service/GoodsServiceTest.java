package shop.goodspia.goods.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.artist.dto.ArtistSaveRequest;
import shop.goodspia.goods.artist.entity.Artist;
import shop.goodspia.goods.common.dto.AccountBank;
import shop.goodspia.goods.goods.dto.GoodsSaveRequest;
import shop.goodspia.goods.goods.entity.Design;
import shop.goodspia.goods.goods.entity.Goods;
import shop.goodspia.goods.goods.repository.DesignRepository;
import shop.goodspia.goods.goods.repository.GoodsQueryRepository;
import shop.goodspia.goods.goods.service.GoodsService;
import shop.goodspia.goods.member.dto.MemberSaveRequest;
import shop.goodspia.goods.exception.ArtistNotFoundException;
import shop.goodspia.goods.member.entity.Member;
import shop.goodspia.goods.artist.repository.ArtistRepository;
import shop.goodspia.goods.goods.repository.GoodsRepository;
import shop.goodspia.goods.member.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@Slf4j
@Transactional
public class GoodsServiceTest {

    private GoodsService goodsService;
    private GoodsRepository goodsRepository = mock(GoodsRepository.class);
    private DesignRepository designRepository = mock(DesignRepository.class);
    private ArtistRepository artistRepository = mock(ArtistRepository.class);
    private MemberRepository memberRepository = mock(MemberRepository.class);
    private GoodsQueryRepository goodsQueryRepository = mock(GoodsQueryRepository.class);

    @BeforeEach
    void beforeEach() {
        goodsService = new GoodsService(goodsRepository, designRepository, artistRepository, goodsQueryRepository);
    }

//    @Test
//    void addGoods() {
//        //아티스트 생성
//        Artist artist = createTestArtist();
//
//        //디자인 옵션 설정
//        List<String> designs = new ArrayList<>();
//        designs.add("design1");
//        designs.add("design2");
//        designs.add("design3");
//
//        GoodsSaveRequest goodsSaveRequest = new GoodsSaveRequest();
//        goodsSaveRequest.setName("goodsName");
//        goodsSaveRequest.setContent("content");
//        goodsSaveRequest.setSummary("summary");
//        goodsSaveRequest.setCategory("category");
//        goodsSaveRequest.setDesigns(designs);
//
//        //굿즈 저장 테스트
//        Long resultId = goodsService.addGoods(artist.getId(), goodsSaveRequest);
//        assertThat(resultId).isNotNull();
//
//        //정상적인 굿즈 인덱스 반환 여부 확인
//        Goods goods = goodsRepository.findById(resultId).get();
//        assertThat(goods.getName()).isEqualTo("goodsName");
//        assertThat(goods.getContent()).isEqualTo("content");
//        assertThat(goods.getSummary()).isEqualTo("summary");
//        assertThat(goods.getCategory()).isEqualTo("category");
//
//        int idx = 1;
//        for (Design design : goods.getDesigns()) {
//            assertThat(design.getDesignName()).isEqualTo("design" + idx);
//            idx++;
//        }
//    }
//
//    @Test
//    void addGoodsArtistNotFound() {
//        GoodsSaveRequest goodsSaveRequest = new GoodsSaveRequest();
//        goodsSaveRequest.setName("goodsName");
//        goodsSaveRequest.setContent("content");
//        goodsSaveRequest.setSummary("summary");
//        goodsSaveRequest.setCategory("category");
//
//        assertThatThrownBy(() -> goodsService.addGoods(1L, goodsSaveRequest)).isInstanceOf(ArtistNotFoundException.class);
//    }
//
//    @Test
//    void convertStringToBankTest() {
//        String bankStr = "KB";
//        AccountBank accountBank = AccountBank.convertStringToBank(bankStr);
//        log.info("accountBank = {}", accountBank);
//        assertThat(accountBank.toString()).isEqualTo(bankStr);
//    }

    private Goods createTestGoods(Artist artist) {
        GoodsSaveRequest goodsSaveRequest = new GoodsSaveRequest();
        goodsSaveRequest.setName("goodsName");
        goodsSaveRequest.setContent("content");
        goodsSaveRequest.setSummary("summary");
        goodsSaveRequest.setCategory("category");
        return goodsRepository.save(Goods.createGoods(goodsSaveRequest, artist));
    }

    private Artist createTestArtist() {
        ArtistSaveRequest artistSaveRequest = new ArtistSaveRequest();
        artistSaveRequest.setNickname("artistNickname");
        artistSaveRequest.setAccountBank("KB");
        artistSaveRequest.setAccountNumber("123123-00-123123");
        artistSaveRequest.setProfileImage("profileImage");
        artistSaveRequest.setPhoneNumber("010-0000-0000");
        return artistRepository.save(Artist.createArtist(artistSaveRequest));
    }

    private Member createTestMember() {
        MemberSaveRequest memberSaveRequest = new MemberSaveRequest();
        memberSaveRequest.setEmail("email");
        memberSaveRequest.setNickname("nickname");
        return memberRepository.save(Member.createMember(memberSaveRequest));
    }
}
