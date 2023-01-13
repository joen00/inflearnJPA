package com.ssafy.board.dto.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.board.entity.member.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardResponseDto {

    private int board_id;
    @JsonIgnore
    private Member member;
    private String board_title;
    private String board_writer;
    private String board_content;
    private int board_count;

    @Builder
    public BoardResponseDto(int board_id, Member member, String board_title, String board_writer, String board_content, int board_count) {
        this.board_id = board_id;
        this.member = member;
        this.board_title = board_title;
        this.board_writer = board_writer;
        this.board_content = board_content;
        this.board_count = board_count;
    }


}
