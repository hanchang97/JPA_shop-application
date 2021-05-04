package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {  // 주문 상품 중 하나임 -> orderitem은 하나의 order 가질 수 있다

    @Id @GeneratedValue
    @Column(name = "order_item_id")  // 칼럼명
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice;  // 주문 가격
    private int count; // 주문 수량

    // == 생성 메서드 == //
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); // 넘어온 수량만큼 재고 감소시켜주기

        return orderItem;
    }


    // == 비즈니스 로직 == //
    public void cancel() { // 주문 취소 시 해당 아이템 수량만큼 복구해줘야 함
        getItem().addStock(count);  // 재고 수량 복구
    }

    // == 조회 로직 == //
    // 해당 아이템 주문 가격 =   아이템 가격 * 수량
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
