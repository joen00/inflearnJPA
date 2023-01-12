package com.ssafy.board.dto;

import com.ssafy.board.entity.member.Member;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MemberDto {
    private int member_id;
    private String nickname;
    private String email;
    private List<BoardDto> boardList = new ArrayList<>();

    public MemberDto(Member member) {
        this.member_id = member.getMember_id();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.boardList = member.getBoardList().stream()
                .map(o -> new BoardDto(o))
                .collect(Collectors.toList());
    }

}
