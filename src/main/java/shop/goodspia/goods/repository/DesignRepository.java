package shop.goodspia.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.entity.Design;

public interface DesignRepository extends JpaRepository<Design, Long> {
}
