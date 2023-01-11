package jakarta.domain.item;

import jakarta.domain.Category;
import jakarta.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //
@DiscriminatorColumn(name="dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비지니스 로직==// 데이터를 가지고 있는 쪽에 있으면 좋다.
    // 재고를 늘리고 줄이기
    // stackQuantity를 변경할 일이 있으면 setter를 쓰지 않고
    // 이런식으로 비지니스 로직을 가지고 변경을 해야하는 것이다.
    // 이안에 필요한 함수를 쓰면서 변경하는 것이 객체지향적이다.

    /**
     * 
     * stock 증가
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /**
     *
     * stock 감소 (0 보다 줄어들면 안된다 체크)
     */
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock <0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
    

}
