package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    // @NotEmpty
    private String name;

    @Embedded   // 내장 타입을 포함했다는 뜻
    private Address address;

    // 하나의 회원이 여러 상품 주문
    @OneToMany(mappedBy = "member")  // Order클래스에 있는 member 필드에 의해 매핑 하겠다는 뜻  / 읽기전용 된다
    private List<Order> orders = new ArrayList<>(); // 멤버와 양방향 이므로  연관관계 주인 정해줘야 한다

    // 테이블에서는 외래키 하나만 변경 해주면 됨 / 객체 입장에서는 두 포인트를 변경해야 함 -> 주인 정해줘야 함
    // 외래키가 가까운 곳을 주인으로!  / 주인 부분은 그대로 놔둔다
    // 컬렉션은 필드에서 초기화하는게 good



}
