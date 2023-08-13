package shop.goodspia.goods.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    private LocalDateTime lastPasswordChanged;
    private String nickname;
    private String lastLoginIp;
    private int isWithdraw;
    private LocalDateTime withdrawTime;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

}
