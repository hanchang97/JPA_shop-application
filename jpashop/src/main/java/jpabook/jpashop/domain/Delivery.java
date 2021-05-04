package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {  // Order과 1:1 매핑 / 서로 한 개씩 가질 수 있다 /  Order에 fk 놓도록 가정
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    // enum 타입 사용할 시 밑에 어노  필요
    @Enumerated(EnumType.STRING)  // ORDINAL은 지양 - 숫자 형태로 나옴
    private DeliveryStatus status;  // READY, COMP

}
