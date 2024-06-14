package shop.goodspia.goods.global.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.goodspia.goods.global.security.domain.Auth;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findAuthByMemberId(Long memberId);
}
