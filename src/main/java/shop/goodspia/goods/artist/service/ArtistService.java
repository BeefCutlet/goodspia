package shop.goodspia.goods.artist.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.artist.dto.ArtistSaveRequest;
import shop.goodspia.goods.artist.dto.ArtistResponse;
import shop.goodspia.goods.artist.dto.ArtistUpdateRequest;
import shop.goodspia.goods.artist.entity.Artist;
import shop.goodspia.goods.member.entity.Member;
import shop.goodspia.goods.artist.repository.ArtistRepository;
import shop.goodspia.goods.member.repository.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final MemberRepository memberRepository;

    /**
     * 아티스트 정보 조회 메서드
     */
    public ArtistResponse getArtistInfo(Long memberId) {
        Artist artist = artistRepository.findArtistByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("아티스트 정보를 찾을 수 없습니다."));

        return new ArtistResponse(artist);
    }

    /**
     * 아티스트 등록 메서드
     */
    public Long registerArtist(Long memberId, ArtistSaveRequest artistSaveRequest) {
        //회원 정보에 아티스트 번호 저장
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        //아티스트 등록
        Artist savedArtist = artistRepository.save(Artist.from(member, artistSaveRequest));

        return savedArtist.getId();
    }

    /**
     * 아티스트 정보 수정 메서드
     */
    public void modifyArtist(Long memberId, ArtistUpdateRequest artistUpdateRequest) {
        Artist artist = artistRepository.findArtistByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("아티스트 정보를 찾을 수 없습니다."));
        artist.updateArtist(artistUpdateRequest);
    }
}
