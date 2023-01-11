package study.datajpa.repository;

import net.bytebuddy.utility.nullability.UnknownNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> { // 엔티티랑, PK 타입

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    // 거의 사용 안한다.
    // namedQuery -> 쿼리이름명 (우선순위)
    // Member에 적은 쿼리를 찾아서 실행시켜준다.
    // namedQuery로 실행 가능함
    // @Query(name = "Member.findByUsername") // 없어도 잘 동작한다. -> Member 타입에 있는 named쿼리가 있으면 실행한다.
    List<Member> findByUsername(@Param("username") String username);

    // 장점 : 오타를 알려준다.(쿼리 오류)
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m from Member m")
    List<String> findUsernameList();

    // 이렇게 하면 dto로 반환할 수 있다.
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    // in절에 알아서 자동으로 실행된다.
    // 이름 여러개 받기
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    // 타입 여러개 쓸 수 있다.
//    List<Member> findListByUsername(String name); //컬렉션
//    Member findMember(String name); //단건
//    Optional<Member> findOptional(String name); //단건 Optional


    // 반환 타입이 페이지인것
    // @Query(value = "select m from Member m left join m.team t", countQuery = "select count(m.username) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    //전체를 한번에 수정하고 싶을때
    // modifying을 빼면 에러가 난다.
    @Modifying(clearAutomatically = true) // 쿼리가 나가고 나서 자동으로 클리어해준다. (영속성을 알아서 없애줌)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    // 1. 엔티티 조인
    // fetch join은 한번에 연관된 애들을 같이 가져온다.
    // 하나의 쿼리문으로 잘 나온다.
    // 엔티티 그래프 => 메서드 이름 + 패치 조인
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    // 2. 엔티티 조인
    @Override // 멤버만 조회하는 findAll + team까지 같이 조회하고 싶은데 (원래는 쿼리로 했다)
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    // 3. 엔티티 조인
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    // 4. 엔티티 조인
    // 아래 두개의 어노테이션중 선택
    @EntityGraph(attributePaths = {"team"}) // => 따로 설정 안해도 가능
    /// @EntityGraph("Member.all") // => member에 NamedEntityGraph를 통해 할 수 있다.
    List<Member> findEntityGraphByUsername(@Param("username") String username);


    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    List<Member> findByUsername(String name);

}
