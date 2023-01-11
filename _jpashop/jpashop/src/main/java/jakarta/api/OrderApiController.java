package jakarta.api;

import jakarta.domain.Address;
import jakarta.domain.Order;
import jakarta.domain.OrderItem;
import jakarta.domain.OrderStatus;
import jakarta.repository.OrderRepository;
import jakarta.repository.OrderSearch;
import jakarta.repository.order.query.OrderFlatDto;
import jakarta.repository.order.query.OrderItemQueryDto;
import jakarta.repository.order.query.OrderQueryDto;
import jakarta.repository.order.query.OrderQueryRepository;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            // 강제 초기화를 해주는 이유 => 프록시를 강제 초기화를 하면 데이터가 있으니깐 뿌려야 겠다고 생각하기 때문이다.
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return result;
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3(){
        // 하지만 1대 다를 fetch join하는 순간 페이징 처리는 불가능하다.
        List<Order> orders = orderRepository.findAllWithItem(); // fetch join으로 쿼리를 사용!! => sql문 한번만 실행한다
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return result;
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                        @RequestParam(value = "limit", defaultValue = "100") int limit){
        // 하지만 1대 다를 fetch join하는 순간 페이징 처리는 불가능하다.
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit); // toone관계는 fetch 조인한다.

        // 루프를 돌면서 orderItem을 가져온다.
        // default_batch_fetch_size => (orders에 관련된)유저들이 주문한 것에 대해 알고 한번의 inquery로 가져온다. orderItems
        // 1: M : N  => 1 : 1: 1이 된다.
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return result;
    }


    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4(){
        // 하지만 1대 다를 fetch join하는 순간 페이징 처리는 불가능하다.
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5(){
        return orderQueryRepository.findAllByDto_optimizaion();
    }

    @GetMapping("/api/v6/orders")
    public List<OrderQueryDto> ordersV6(){
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();
        // OrderQueryDto의 타입으로 반환하고 싶으면 루프를 돌려서 중복을 삭제시켜주면 된다.

        return flats.stream()
                .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                        mapping(o -> new OrderItemQueryDto(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
                )).entrySet().stream()
                .map(e -> new OrderQueryDto(e.getKey().getOrderId(), e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(), e.getKey().getAddress(), e.getValue()))
                .collect(toList());

    }

    @Data
    static class OrderDto{ // dto로 반환하는 것은 dto안에 엔티티가 있으면 안된다(매핑하는 것도 안됨, 결국 외부에 노출이 된다는 것)

        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems; // => 안나옴 => entity라서
        // private List<OrderItem> orderItems;
        // 즉 OrderItem 조차도 바꿔야 한다.

        public OrderDto(Order order){
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            // 똑같이 돌려서 dto로 만들어준다.
            orderItems = order.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDto(orderItem))
                    .collect(toList());

        }
    }

    @Getter
    static class OrderItemDto{

        // 아이템안에 상품명만 필요해
        private String itemName; // 상품명
        private int orderPrice; // 주문 가격
        private int count; // 주문 수량

        public OrderItemDto(OrderItem orderItem){
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }

    }

}
