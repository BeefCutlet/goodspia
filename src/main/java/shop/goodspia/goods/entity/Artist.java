package shop.goodspia.goods.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Artist extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "artist_id")
    private Long id;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private AccountBank accountBank;
    private String accountNumber;
    private String phoneNumber;
    private LocalDateTime createdTime;
}
