package shop.goodspia.goods.api.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.goodspia.goods.api.goods.entity.Design;

import java.util.List;

public interface DesignRepository extends JpaRepository<Design, Long> {

    @Query("select d from Design d where d.goods.id = :goodsId")
    List<Design> findAllByGoodsId(Long goodsId);
}
