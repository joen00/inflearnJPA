package com.ssafy.board.service.member;

import com.ssafy.board.dto.member.MemberRequestDto;
import com.ssafy.board.entity.member.Member;
import com.ssafy.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void save(MemberRequestDto memberRequestDto){
        memberRepository.save(memberRequestDto.ToEntity());
    }

    @Transactional
    public List<MemberRequestDto> getMemberList(){
        List<Member> all = memberRepository.findAll();
        List<MemberRequestDto> memberDtoList = new ArrayList<>();

        for (Member member : all){
            MemberRequestDto memberDto = MemberRequestDto.builder()
                    .nickname(member.getNickname())
                    .email(member.getEmail())
                    .boardList(member.getBoardList())
                    .build();
            memberDtoList.add(memberDto);

        }
        return memberDtoList;
    }

}
