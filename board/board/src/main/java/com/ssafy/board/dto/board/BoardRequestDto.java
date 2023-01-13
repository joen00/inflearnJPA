package com.ssafy.board.dto.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.board.entity.board.Board;
import com.ssafy.board.entity.member.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardRequestDto {

    private int board_id;
    private Member member;
    private String board_title;
    private String board_writer;
    private String board_content;
    private int board_count;

    @Builder
    public BoardRequestDto(Member member, int board_id, String board_title, String board_writer, String board_content, int board_count) {
        this.member = member;
        this.board_id = board_id;
        this.board_title = board_title;
        this.board_writer = board_writer;
        this.board_content = board_content;
        this.board_count = board_count;
    }

    public Board ToEntity(){
        return Board.builder()
                .member(member)
                .board_title(board_title)
                .board_content(board_content)
                .board_writer(board_writer)
                .board_count(board_count)
                .build();
    }

}
