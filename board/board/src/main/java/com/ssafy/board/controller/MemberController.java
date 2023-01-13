package com.ssafy.board.controller;


import com.ssafy.board.dto.member.MemberRequestDto;
import com.ssafy.board.dto.member.MemberResponseDto;
import com.ssafy.board.repository.MemberRepository;
import com.ssafy.board.service.member.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Api("멤바 컨트롤러 API V1")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @ApiOperation(value = "멤버 작성", notes = "새로운 멤버 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PostMapping("/member")
    public ResponseEntity<String> save(@ApiParam(value = "BoardDto", required = true) @RequestBody MemberRequestDto request){
        boolean result = memberService.save(request);
        if (result)
            return new ResponseEntity<String>("success", HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "멤버 리스트 전체 조회", notes = "성공하면 멤버 리스트 전체의 값이 나온다.", response = String.class)
    @GetMapping("/member")
    public ResponseEntity<?> getMemberList(){
        return new  ResponseEntity(memberService.getMemberList(), HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "멤버 삭제", notes = "성공하면 success.", response = String.class)
    @DeleteMapping("/member/{id}")
    public ResponseEntity<?> delete(@ApiParam(value = "BoardDto", required = true) @PathVariable("id") int id){
        memberService.deletePost(id);
        return new ResponseEntity("success", HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "멤버 수정", notes = "멤버의 id값을 받고 수정한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PutMapping("/member/{id}")
    public ResponseEntity<?> updatePost(@ApiParam(value = "BoardDto", required = true) @PathVariable("id") int  id,
                                        @ApiParam(value = "BoardDto", required = true) @RequestBody MemberRequestDto request){
        // memberService.update(id, request);
        boolean result = memberService.update(id, request);
        if (result)
            return new ResponseEntity<String>("success", HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
    }

    
    // Dto 생성 방식
    //    @GetMapping("/member")
//    public List<MemberDto> read(){
//        List<Member> members = memberRepository.findAll();
//        List<MemberDto> result = members.stream()
//                .map(o -> new MemberDto(o))
//                .collect(Collectors.toList());
//        return result;
//    }

//    @PostMapping("/board")
//    public CreateMemberResponse saveMember(@RequestBody CreateMemberRequest request){
//        Member member = new Member(request.getNickname(), request.getEmail());
//        Member members = memberRepository.save(member);
//        return new CreateMemberResponse(members.getMember_id());
//    }

//    @Data
//    static class CreateMemberRequest {
//        private String nickname;
//        private String email;
//    }
//
//    @Data
//    static class CreateMemberResponse {
//        private int id;
//        public CreateMemberResponse(int id) {
//            this.id = id;
//        }
//    }

}
