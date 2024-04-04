package shop.goodspia.goods.member.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Address {

    @Column
    private String zipcode;
    @Column
    private String address1;
    @Column
    private String address2;

    public static Address of(String zipcode, String address1, String address2) {
        return new Address(zipcode, address1, address2);
    }
}
