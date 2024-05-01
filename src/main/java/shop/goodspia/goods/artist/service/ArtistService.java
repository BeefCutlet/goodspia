package shop.goodspia.goods.artist.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.goodspia.goods.artist.dto.ArtistResponse;
import shop.goodspia.goods.artist.dto.ArtistSaveRequest;
import shop.goodspia.goods.artist.dto.ArtistUpdateRequest;
import shop.goodspia.goods.artist.entity.Artist;
import shop.goodspia.goods.artist.repository.ArtistRepository;
import shop.goodspia.goods.common.util.ImagePath;
import shop.goodspia.goods.common.util.ImageUpload;
import shop.goodspia.goods.member.entity.Member;
import shop.goodspia.goods.member.repository.MemberRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final MemberRepository memberRepository;
    private final ImageUpload imageUpload;

    /**
     * 아티스트 정보 조회 메서드
     */
    public ArtistResponse getArtistInfo(Long memberId) {
        Artist artist = artistRepository.findArtistByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("아티스트로 등록되지 않았습니다."));

        return ArtistResponse.from(artist);
    }

    /**
     * 아티스트 등록 메서드
     */
    public void registerArtist(Long memberId, ArtistSaveRequest artistSaveRequest) {
        //회원 정보에 아티스트 번호 저장
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        //이미 등록된 아티스트인지 확인
        Optional<Artist> findArtist = artistRepository.findArtistByMemberId(memberId);
        if (findArtist.isPresent()) {
            throw new IllegalStateException("이미 등록된 아티스트입니다.");
        }

        //아티스트 등록
        artistRepository.save(Artist.from(member, artistSaveRequest));
    }

    /**
     * 아티스트 정보 수정 메서드
     */
    public void modifyArtist(Long memberId, ArtistUpdateRequest artistUpdateRequest, MultipartFile profileImage) {
        //아티스트의 프로필 이미지 저장(갱신)
        String profileImageUrl = null;
        if (!profileImage.isEmpty()) {
            profileImageUrl = imageUpload.uploadImage(profileImage, ImagePath.PROFILE_IMAGE);
        }

        Artist artist = artistRepository.findArtistByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("아티스트 정보를 찾을 수 없습니다."));
        artist.updateArtist(artistUpdateRequest, profileImageUrl);
    }
}
