package shop.goodspia.goods.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.security.entity.Auth;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findAuthByMemberId(Long memberId);
}
