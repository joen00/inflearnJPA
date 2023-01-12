package com.ssafy.board.dto.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.board.dto.board.BoardDto;
import com.ssafy.board.entity.board.Board;
import com.ssafy.board.entity.member.Member;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@NoArgsConstructor
@Getter
public class MemberRequestDto {

    private int member_id;
    private String nickname;
    private String email;
    private List<Board> boardList = new ArrayList<>();

    // 요청하는 것
    @Builder
    public MemberRequestDto(String nickname, String email, List<Board> boardList) {
        this.nickname = nickname;
        this.email = email;
        this.boardList = boardList.stream()
                .map(o -> new Board(o))
                .collect(Collectors.toList());
    }

    public Member ToEntity(){
        return Member.builder()
                .nickname(this.nickname)
                .email(this.email)
                .boardList(this.boardList)
                .build();
    }

}
