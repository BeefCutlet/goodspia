package shop.goodspia.goods.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.goods.entity.Goods;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
}
