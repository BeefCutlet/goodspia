package shop.goodspia.goods.security.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.goodspia.goods.member.entity.Member;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Auth {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id")
    private Long id;
    private String refreshToken;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Auth(String refreshToken, Member member) {
        this.refreshToken = refreshToken;
        this.member = member;
    }

    public Auth updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }
}
