package com.ssafy.board.controller;

import com.ssafy.board.dto.MemberDto;
import com.ssafy.board.entity.Member;
import com.ssafy.board.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/member")
    public List<MemberDto> read(){
        List<Member> members = memberRepository.findAll();
        List<MemberDto> result = members.stream()
                .map(o -> new MemberDto(o))
                .collect(Collectors.toList());
        return result;
    }

    @PostMapping("/board")
    public CreateMemberResponse saveMember(@RequestBody CreateMemberRequest request){
        Member member = new Member(request.getNickname(), request.getEmail());
        Member members = memberRepository.save(member);
        return new CreateMemberResponse(members.getMember_id());
    }

    @Data
    static class CreateMemberRequest {
        private String nickname;
        private String email;
    }

    @Data
    static class CreateMemberResponse {
        private int id;
        public CreateMemberResponse(int id) {
            this.id = id;
        }
    }

}
