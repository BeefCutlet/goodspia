package shop.goodspia.goods.entity;

import lombok.Getter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class BaseTimeEntity {

    private LocalDateTime createdTime;
    private LocalDateTime lastUpdatedTime;
}
