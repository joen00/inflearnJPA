package jakarta.controller;

import jakarta.domain.Address;
import jakarta.domain.Member;
import jakarta.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createFrom(Model model){ // model 은
        model.addAttribute("memberForm", new MemberForm()); // memberForm이라는 빈 껍데기를 가지고 간다.
        return "members/createMemberForm";
    }

    // validation 안한거임 @Valid MemberForm form
    @PostMapping("/members/new")
    public String create(MemberForm form, BindingResult result){

        if(result.hasErrors()){
            return "members/createMemberForm"; // html에서 error가 있으면 다르게 하겠다고 해줌
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
