package shop.goodspia.goods.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.artist.dto.ArtistUpdateRequest;
import shop.goodspia.goods.artist.entity.Artist;
import shop.goodspia.goods.artist.repository.ArtistRepository;
import shop.goodspia.goods.artist.service.ArtistService;
import shop.goodspia.goods.common.dto.AccountBank;
import shop.goodspia.goods.member.repository.MemberRepository;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
@Transactional
public class ArtistServiceTest {

    private ArtistService artistService;
    private ArtistRepository artistRepository = mock(ArtistRepository.class);
    private MemberRepository memberRepository = mock(MemberRepository.class);

    @BeforeEach
    void beforeEach() {
        artistService = new ArtistService(artistRepository, memberRepository);
    }

    @Test
    void modifyArtistTest() {
        Artist artist = Artist.builder()
                .nickname("ArtistNickname")
                .profileImage("ArtistProfileImage")
                .accountBank(AccountBank.KB)
                .accountNumber("123123-00-123123")
                .phoneNumber("010-0000-0000")
                .build();
        when(artistRepository.findById(1L)).thenReturn(Optional.of(artist));

        ArtistUpdateRequest artistUpdateRequest = new ArtistUpdateRequest();
    }
}
