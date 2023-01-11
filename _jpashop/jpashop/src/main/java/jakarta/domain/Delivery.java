package jakarta.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name="delivery_id")
    private  Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    // @JoinColumn(name="delivery")
    private Order order;

    @Embedded
    private  Address address;


    // ORDINAL => 1, 2, 3,4 숫자로 만듬 따라서 중간에 다른 상태가 생기면 망하는 것이다.
    // 따라서 STRING으로 써야함
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY, COMP


}
