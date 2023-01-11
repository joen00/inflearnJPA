package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username","age"}) // 연관관계 필드는 to string하지 말자
@NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.username = :username" // 오타를 치면 오타를 알려준다 error in named query
)
@NamedEntityGraph(name = "Member.all", attributeNodes = @NamedAttributeNode("team"))
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username){
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if(team != null){
            changeTeam(team);
        }
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public void changeTeam(Team team){
        this.team = team; // 내것만 바꾸는게 아니라 반대쪽도 바꿔줘야하기 때문이다.
        team.getMembers().add(this); // 팀에 있는 멤버에도 걸어주도록 한다.
    }

//    public void changeUsername(String username){
//        this.username = username;
//    }


}
