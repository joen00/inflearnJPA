package com.ssafy.board.service.member;

import com.ssafy.board.dto.member.MemberRequestDto;
import com.ssafy.board.entity.member.Member;
import com.ssafy.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public boolean save(MemberRequestDto memberRequestDto){
        Member member = memberRepository.save(memberRequestDto.ToEntity());
        if (member == null){
            return false;
        }else return true;
    }

    @Transactional
    public List<MemberRequestDto> getMemberList(){
        List<Member> all = memberRepository.findAll();
        List<MemberRequestDto> memberDtoList = new ArrayList<>();

        for (Member member : all){
            MemberRequestDto memberDto = MemberRequestDto.builder()
                    .member_id(member.getMember_id())
                    .nickname(member.getNickname())
                    .email(member.getEmail())
                    .boardList(member.getBoardList())
                    .build();
            memberDtoList.add(memberDto);

        }
        return memberDtoList;
    }

    @Transactional
    public void deletePost(int id){
        memberRepository.deleteById(id);
    }

    @Transactional
    public boolean update(int id, MemberRequestDto dto){
        Optional<Member> byId = memberRepository.findById(id);
        Member member = byId.get();
        return member.updateMember(dto.getNickname(), dto.getEmail());
    }

}
