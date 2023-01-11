package jakarta.repository;


import jakarta.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // @PersistenceContext => RequiredArgsConstructor(알아서 생성자 만들어줌)
    private final EntityManager em; // 엔티티 매니저 만들어서 주입해줌

    public void save(Member member) {
        em.persist(member); // db에 insert 쿼리가 날라간다.
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id); // 단건 조회할때 첫번째는 (타입,PK)
    }

    public List<Member> findAll() {
        // jpql은 엔티티 객체에 대한 것을 조회한다고 보면 된다. => jpql은 기본편에
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
        // 쿼리, 반환 타입
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name= :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}