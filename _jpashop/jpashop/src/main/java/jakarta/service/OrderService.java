package jakarta.service;

import jakarta.domain.Delivery;
import jakarta.domain.Member;
import jakarta.domain.Order;
import jakarta.domain.OrderItem;
import jakarta.domain.item.Item;
import jakarta.repository.ItemRepository;
import jakarta.repository.MemberRepository;
import jakarta.repository.OrderRepository;
import jakarta.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order); // order를 persist하면 알아서 다 persist해준다.
        // -> 이유>? castcadeAll를 해줬기 때문
        // -> 결과>? 따라서 delivery, orderItem 이 자동으로 persist해줌
        // -> castcade의 범위는 ? order의 경우 참조하는게 주인이 pk일 경우에만 써야한다. orderItem 이 order만 참조하기 때문

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
    }

    // 검색

     public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByCriteria(orderSearch);
     }


}
