package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // 전략 설정
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")  // 칼럼명
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> caategories = new ArrayList<>();

    // == 비즈니스 로직 == //
    public void addStock(int quantity) {
        this.stockQuantity += quantity;   // 재고 수량 증가 로직  / 즉 주문 취소 시 다시 재고 증가
    }

    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;  // 재고 수량 감소 로직
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
