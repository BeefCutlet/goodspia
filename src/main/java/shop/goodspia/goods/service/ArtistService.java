package shop.goodspia.goods.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.dto.ArtistDto;
import shop.goodspia.goods.entity.Artist;
import shop.goodspia.goods.entity.Member;
import shop.goodspia.goods.exception.ArtistNotFoundException;
import shop.goodspia.goods.exception.MemberNotFoundException;
import shop.goodspia.goods.repository.ArtistRepository;
import shop.goodspia.goods.repository.MemberRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final MemberRepository memberRepository;

    /**
     * 아티스트 등록 메서드
     */
    public Long registerArtist(Long memberId, ArtistDto artistDto) {
        //회원 정보에 아티스트 번호 저장
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("회원정보가 존재하지 않습니다."));

        //아티스트 등록
        Artist savedArtist = artistRepository.save(Artist.createArtist(artistDto));
        //회원 정보에 아티스트 연관관계 추가
        member.registerArtist(savedArtist);

        return savedArtist.getId();
    }

    /**
     * 아티스트 정보 수정 메서드
     */
    public void modifyArtist(ArtistDto artistDto) {
        Artist artist = artistRepository.findById(artistDto.getId())
                .orElseThrow(() -> new ArtistNotFoundException("아티스트로 등록되지 않았습니다."));
        artist.updateArtist(artistDto);
    }
}
