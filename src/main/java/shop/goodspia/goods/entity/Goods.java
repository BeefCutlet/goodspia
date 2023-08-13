package shop.goodspia.goods.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
public class Goods extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "goods_id")
    private Long id;
    private String name;
    private String summary;
    private String content;
    private String category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @OneToMany(mappedBy = "design")
    private List<Design> designs = new ArrayList<>();

}
