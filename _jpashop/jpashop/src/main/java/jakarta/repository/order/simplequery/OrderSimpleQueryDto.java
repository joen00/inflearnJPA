package jakarta.repository.order.simplequery;

import jakarta.domain.Address;
import jakarta.domain.Order;
import jakarta.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderSimpleQueryDto {
    private Long orderId; // 엔티티 이름인 id가 아니라 orderId로 나올수 있게 해준다.
    private String name;
    private LocalDateTime orderDate; //주문시간
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate,OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name =  name;// LAZY 초기화
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address =  address;// LAZY 초기화
    }

}