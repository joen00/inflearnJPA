package jakarta.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded // 내장타입을 썼다는 것
    private Address address;

    // 1: 다 관계 
    // order 테이블에 있는 매핑된 거울이 되는 거야
    @JsonIgnore // => 다른 api를 만들때 문제가 생긴다. => 양방향이 걸리는 곳에 다 넣어줘야 한다.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}