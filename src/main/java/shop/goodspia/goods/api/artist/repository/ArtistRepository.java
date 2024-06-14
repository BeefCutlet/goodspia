package shop.goodspia.goods.api.artist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.goodspia.goods.api.artist.entity.Artist;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    @Query("select a from Artist a where a.member.id = :memberId")
    Optional<Artist> findArtistByMemberId(final Long memberId);
}
