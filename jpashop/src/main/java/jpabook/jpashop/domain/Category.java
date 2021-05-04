package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany  // 다대다는 디비에서 테이블 빼줘야함
    @JoinTable(name = "category_item",
        joinColumns = @JoinColumn(name="category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;  // 카테고리는 한 개의 카테고리 부모 존재

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();


    // 연관 관계 편의 메소드
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }

}
