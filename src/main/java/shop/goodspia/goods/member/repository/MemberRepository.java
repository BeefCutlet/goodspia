package shop.goodspia.goods.member.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.goodspia.goods.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @EntityGraph(attributePaths = "artist")
    Optional<Member> findByEmail(String email);

    @Query("select m from Member m where m.email = :email")
    Optional<Member> findByEmailNotFetch(String email);
}
