package jakarta.api;

import jakarta.domain.Member;
import jakarta.domain.Member;
import jakarta.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

//@Controller @ResponseBody => 두개 합친거 RestController
// RestController => json 이나 xml로 바로 보내자
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    // json 값이 와야하는데 array에 쌓여진 json이 온다.
    @GetMapping("/api/v1/members")
    public List<Member> membersV1(){
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2(){
        List<Member> findMembers = memberService.findMembers();
        // for문 돌려도 됨
        List<MemberDto> collect = findMembers.stream() 
                .map(m -> new MemberDto((m.getName())))
                .collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }

    // api를 만들때는 엔티티를 파라미터로 받지 말자!!!

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) { // RequestBody => json으로 온 body를 member로 바꿔줌
        Long id = memberService.join(member);               // Valid 로 NotEmpty같은거 java Validation을 검증해준다.
        return new CreateMemberResponse(id);
    }

    /**
     * @param request
     */
    // 1번과 2번 비교 : 1번이 주는 유일한 장점은 클래스를 안만들어도 된다.
    // 하지만 엔티티가 변하면 너무 어려워진다. 엔티티를 변경해도 api 스펙이 변하지 않는다.
    // member의 파라미터가 뭐가 올지 모른다. (id, name, address)중 모른다.
    // 이렇게 dto를 받으면 name만 받는 구나를 안다.
    // Not empty를 여기서 넣는다.
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id,request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest{
        private String name;
    }

    @Data
    @AllArgsConstructor // 모든 파라미터 넘기는 생성자 필요
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

}
