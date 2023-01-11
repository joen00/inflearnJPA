package jakarta.service;

import jakarta.domain.Member;
import jakarta.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest { // Repository , Service까지 만들고 테스트

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    // 이런게 주어졌을떄 이렇게 하면 이렇게 된다.

    // @Rollback(value = false) // => 쿼리를 보고싶을때 // DB에서 눈으로 볼 수 있다.
    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        // em.flush(); // 그러면 쿼리로 잘 나온다.
        assertEquals(member, memberRepository.findOne(saveId));
        //멤버가 멤버 리포지토리에서 찾아온 멤머랑 똑같은게 나오면 가입이 된것
    }

    @Test(expected = IllegalStateException.class) // try catch 안해도 됨, 여기에 터져서 나가는게 적은 예외면 알아서 나간다.
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생 해야한다!!!



        //then
        fail(" 예외가 발생해야 한다. ");
    }
    
}