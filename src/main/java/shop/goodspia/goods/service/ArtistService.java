package shop.goodspia.goods.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.dto.artist.ArtistRequestDto;
import shop.goodspia.goods.dto.artist.ArtistResponseDto;
import shop.goodspia.goods.entity.Artist;
import shop.goodspia.goods.entity.Member;
import shop.goodspia.goods.exception.ArtistNotFoundException;
import shop.goodspia.goods.exception.MemberNotFoundException;
import shop.goodspia.goods.repository.ArtistRepository;
import shop.goodspia.goods.repository.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final MemberRepository memberRepository;

    /**
     * 아티스트 정보 조회 메서드
     * @param artistId
     * @return
     */
    public ArtistResponseDto getArtistInfo(long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ArtistNotFoundException("Artist Data Not Found"));

        return ArtistResponseDto.builder()
                .nickname(artist.getNickname())
                .profileImage(artist.getProfileImage())
                .accountBank(artist.getAccountBank().toString())
                .accountNumber(artist.getAccountNumber())
                .phoneNumber(artist.getPhoneNumber())
                .build();
    }

    /**
     * 아티스트 등록 메서드
     */
    public Long registerArtist(Long memberId, ArtistRequestDto artistRequestDto) {
        //회원 정보에 아티스트 번호 저장
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member Data Not Found"));

        //아티스트 등록
        Artist savedArtist = artistRepository.save(Artist.createArtist(artistRequestDto));
        //회원 정보에 아티스트 연관관계 추가
        member.registerArtist(savedArtist);

        return savedArtist.getId();
    }

    /**
     * 아티스트 정보 수정 메서드
     */
    public void modifyArtist(ArtistRequestDto artistRequestDto) {
        Artist artist = artistRepository.findById(artistRequestDto.getId())
                .orElseThrow(() -> new ArtistNotFoundException("Artist Data Not Found"));
        artist.updateArtist(artistRequestDto);
    }
}
