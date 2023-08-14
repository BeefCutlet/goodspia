package shop.goodspia.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.entity.Goods;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
}
