package shop.goodspia.goods.artist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.artist.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
