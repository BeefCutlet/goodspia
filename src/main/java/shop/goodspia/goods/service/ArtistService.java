package shop.goodspia.goods.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.goodspia.goods.dto.ArtistDto;
import shop.goodspia.goods.entity.Artist;
import shop.goodspia.goods.exception.ArtistNotFoundException;
import shop.goodspia.goods.repository.ArtistRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ArtistService {

    private final ArtistRepository artistRepository;

    /**
     * 아티스트 등록 메서드
     */
    public Long registerArtist(ArtistDto artistDto) {
        Artist savedArtist = artistRepository.save(Artist.createArtist(artistDto));
        return savedArtist.getId();
    }

    /**
     * 아티스트 정보 수정 메서드
     */
    public void modifyArtist(ArtistDto artistDto) {
        Optional.ofNullable(artistRepository.findById(artistDto.getId()))
                .ifPresentOrElse(
                        artist -> artist.get().updateArtist(artistDto),
                        () -> new ArtistNotFoundException("아티스트로 등록되지 않았습니다."));
    }
}
