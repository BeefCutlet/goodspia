package shop.goodspia.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
