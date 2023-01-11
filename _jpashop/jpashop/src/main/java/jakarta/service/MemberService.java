package jakarta.service;

import jakarta.domain.Member;
import jakarta.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 3단계
//@AllArgsConstructor // 생성자를 만들어줌
@Service
@Transactional(readOnly = true) // 그냥 단순한 읽기전용으로만 해라 -> 기본적으로 전체에 적용
@RequiredArgsConstructor // 딱 final만 만들어줌
public class MemberService {

    private final MemberRepository memberRepository; // 테스트를 할때 못바꾼다.

    // 2단계
//    public MemberService(MemberRepository memberRepository){ // 생성자에서 인젝션을 해줌
//        this.memberRepository = memberRepository;
//    }

    // 1단계
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) { // 바로 주입하는게 아니라 이렇게 들어와서 해줌
//        this.memberRepository = memberRepository;                        // 단점은
//    }

    /*
     * 회원가입
     */
    @Transactional // 읽기가 아닌 쓰기는 리드온리 넣으면 안됨 -> 따로 설정하면 우선권을 가짐
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증하는 함수
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // 예외처리
        List<Member> findMembers = memberRepository.findByName(member.getName()); // 이름을 유니크로 잡는게 더 좋다
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 하나 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
