package com.ssafy.board.dto.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.board.dto.board.BoardDto;
import com.ssafy.board.entity.board.Board;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MemberResponseDto {

    private int member_id;
    private String nickname;
    private String email;

    private List<Board> boardList = new ArrayList<>();

    @Builder
    public MemberResponseDto(String nickname, String email, List<Board> boardList) {
        this.nickname = nickname;
        this.email = email;
        this.boardList = boardList.stream()
                .map(o -> new Board(o))
                .collect(Collectors.toList());
    }

}
