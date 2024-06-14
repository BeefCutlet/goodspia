package shop.goodspia.goods.api.wish.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.goodspia.goods.api.wish.entity.Wish;

import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {

    @Query("select w from Wish w where w.member.id = :memberId and w.goods.id = :goodsId")
    Optional<Wish> findWishByMemberIdAndGoodsId(Long memberId, Long goodsId);
}
