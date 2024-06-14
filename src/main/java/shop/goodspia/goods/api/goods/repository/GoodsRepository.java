package shop.goodspia.goods.api.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.goodspia.goods.api.goods.entity.Goods;

import java.util.Optional;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Query("select g from Goods g where g.id = :goodsId and g.isDeleted = 0")
    Optional<Goods> findByGoodsIdNotDeleted(Long goodsId);
}
