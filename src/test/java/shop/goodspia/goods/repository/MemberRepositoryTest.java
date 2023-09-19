package shop.goodspia.goods.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.artist.dto.ArtistSaveRequest;
import shop.goodspia.goods.artist.repository.ArtistRepository;
import shop.goodspia.goods.member.dto.MemberSaveRequest;
import shop.goodspia.goods.common.dto.AccountBank;
import shop.goodspia.goods.artist.entity.Artist;
import shop.goodspia.goods.member.entity.Member;
import shop.goodspia.goods.member.repository.MemberRepository;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Test
    void findByEmailSuccess() {
        //given - 회원 정보 생성 및 저장
        Member member = createMember();
        Member savedMember = memberRepository.save(member);

        //given - 아티스트 정보 생성 및 저장
        Artist artist = createArtist();
        Artist savedArtist = artistRepository.save(artist);
        //given - 연관관계 설정
        savedMember.registerArtist(artist);

        //when - 저장된 회원정보 조회
        Member findMember = memberRepository.findByEmail(savedMember.getEmail()).get();

        //then
        assertThat(findMember.getEmail()).isEqualTo("MemberEmail");
        assertThat(findMember.getNickname()).isEqualTo("MemberNickname");
        assertThat(findMember.getPassword()).isEqualTo("MemberPassword");
        assertThat(findMember.getIsWithdraw()).isEqualTo(0);
        assertThat(findMember.getArtist().getNickname()).isEqualTo("ArtistNickname");
        assertThat(findMember.getArtist().getProfileImage()).isEqualTo("ArtistProfileImage.png");
        assertThat(findMember.getArtist().getAccountBank()).isEqualTo(AccountBank.KB);
        assertThat(findMember.getArtist().getAccountNumber()).isEqualTo("123123-00-123123");
        assertThat(findMember.getArtist().getPhoneNumber()).isEqualTo("010-1234-1234");
    }

    private Member createMember() {
        return Member.builder()
                .email("MemberEmail")
                .nickname("MemberNickname")
                .password("MemberPassword")
                .isWithdraw(0)
                .build();
    }

    private Artist createArtist() {
        return Artist.builder()
                .nickname("ArtistNickname")
                .profileImage("ArtistProfileImage.png")
                .accountBank(AccountBank.KB)
                .accountNumber("123123-00-123123")
                .phoneNumber("010-1234-1234")
                .build();
    }
}
