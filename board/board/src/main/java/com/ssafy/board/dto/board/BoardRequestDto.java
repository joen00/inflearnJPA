package com.ssafy.board.dto.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.board.entity.board.Board;
import com.ssafy.board.entity.member.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ApiModel(value = "BoardRequestDto : 멤버 정보", description = "게시판 상세 정보를 나타낸다.")
public class BoardRequestDto {

    @ApiParam(value = "게시판 FK 멤버", required = true)
    private Member member;
    @ApiParam(value = "게시판 제목", required = true)
    private String board_title;
    @ApiParam(value = "게시판 글쓴이", required = true)
    private String board_writer;
    @ApiParam(value = "게시판 글", required = true)
    private String board_content;

    @Builder
    public BoardRequestDto(Member member, String board_title, String board_writer, String board_content) {
        this.member = member;
        this.board_title = board_title;
        this.board_writer = board_writer;
        this.board_content = board_content;
    }

    public Board ToEntity(){
        return Board.builder()
                .member(member)
                .board_title(board_title)
                .board_content(board_content)
                .board_writer(member.getNickname())
                .build();
    }

}
