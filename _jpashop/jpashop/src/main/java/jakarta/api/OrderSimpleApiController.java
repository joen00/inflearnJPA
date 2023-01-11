package jakarta.api;

import jakarta.domain.Address;
import jakarta.domain.Order;
import jakarta.domain.OrderStatus;
import jakarta.repository.OrderRepository;
import jakarta.repository.OrderSearch;
import jakarta.repository.order.simplequery.OrderSimpleQueryDto;
import jakarta.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * XToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    // 안되는 이유
    // 1번 . Order -> member 호출 member -> order 호출 => 양방향 호출로 무한 로프가 걸린다.
    // 그때 막아주는게 걸리는 곳에 @JsonIgnore를 붙어햐하는데 그게 별로다.
    // 2번
    // order의 fetch가 LAZY가 지연로딩이 되어있어서 프록시 객체를 가짜로 넣어놓고 작업을 해아한다.
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        System.out.println("hi");
        List<Order> all = orderRepository.findAllByString(new OrderSearch()); // 지연 로딩이라서
        // hibernate5는 지연로딩을 무시하는 것이다.
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;
    }
    // 레이지 로딩으로 인한 너무 많은 쿼리가 온다는 단점이 있다. -> 3개의 테이블을 검색해서 나가야 한다.


    // v3, v4는 우열을 가리기 어렵다.
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(); // query로 가져와서
        // 원하는 dto로 만드는 것이다.
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() { // dto 조인한거는 값을 변경하기 어렵다. (?) 코드상 지저분 하다.
        return  orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId; // 엔티티 이름인 id가 아니라 orderId로 나올수 있게 해준다.
        private String name;
        private LocalDateTime orderDate; //주문시간
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); // LAZY 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // LAZY 초기화
        }

    }

}
