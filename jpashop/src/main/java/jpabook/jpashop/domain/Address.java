package jpabook.jpashop.domain;

import lombok.Getter;


import javax.persistence.Embeddable;

@Embeddable   // 내장될수 있다는 뜻 / 값 타입   / 값 타입은 변경 불가능하게 설계해야 한다
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address(){  // jpa 스펙 상 만들어줘야 하는 기본 생성자

    }

    public Address(String city, String street, String zipcode) {  // setter 대신 생성자
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
