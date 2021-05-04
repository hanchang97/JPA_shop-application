package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {    // 한명의 회원이 여러 주문 가능  /  한 주문안에서 여러 상품도 된다 -> 이건 order과 oderitem 관계

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")   // 매핑  외래키 이름 = member_id  /  자기 주문한 회원 매핑
    private Member member;  // 멤버와의 양방향 관계에서 주인!

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)  // OrderItem 클래스의 order 필드에 매핑
    private List<OrderItem> orderItems = new ArrayList<>();  // cascade -> order 저장시 orderItem도 같이 저장

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)  // 모든 엔티티는 원래 각각 persist해야함
    @JoinColumn(name="delivery_id")            // cascade -> order만 persist하면 delivery도 같이!
    private Delivery delivery;  // Order과 Delivery와의 관계에서 주인
    // cacade -> order persist될 때 delivery 엔티티도 persist

    private LocalDateTime orderDate;  // 주문 시간  // java8 부터는 LocalDateTime 사용해도 hibernate가 자동 지원
    // annotation으로 따로 매핑 필요x

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // enum  /  주문 상태


    // ** 연관관계 편의 메소드 ** //    편의 메소드 위치는 핵심적으로 컨트롤하는 대상에 있으면 굳 / 양방향에서 편의 메소드 있는게 좋다
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // == 생성 메서드 == //      // ... = 여러개 넘긴다    / 주문생성 시 현 메소드만 보면되서 관리 편리하다
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    // ==  비즈니스 로직 == //
    // 주문취소
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();  // 아이템도 각각 하나씩 취소 기능 필요
        }
    }

    // == 조회 로직 == //
    public int getTotalPrice(){
         int totalPrice = 0;
         for(OrderItem orderItem : orderItems){
             totalPrice += orderItem.getTotalPrice();
         }
         return totalPrice;
    }
}
