package com.ssafy.board.dto.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.board.entity.member.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@ApiModel(value = "BoardResponseDto : 멤버 정보", description = "게시판 정보를 나타낸다.")
public class BoardResponseDto {

    @ApiParam(value = "게시판 id", required = true)
    private int board_id;
    // JsonIgnore => 없으면 serialization 오류남
    @ApiParam(value = "게시판 FK 멤버", required = true)
    @JsonIgnore
    private Member member;
    @ApiParam(value = "게시판 제목", required = true)
    private String board_title;
    @ApiParam(value = "게시판 글쓴이", required = true)
    private String board_writer;
    @ApiParam(value = "게시판 글", required = true)
    private String board_content;
    @ApiParam(value = "게시판 조회수", required = true)
    private int board_count;
    @ApiParam(value = "생성 시간", required = true)
    private ZonedDateTime created_date;
    @ApiParam(value = "업데이트 시간", required = true)
    private  ZonedDateTime updated_date;

    @Builder
    public BoardResponseDto(int board_id, Member member, String board_title,
                            String board_writer, String board_content, int board_count,
                            ZonedDateTime created_date, ZonedDateTime updated_date) {
        this.board_id = board_id;
        this.member = member;
        this.board_title = board_title;
        this.board_writer = board_writer;
        this.board_content = board_content;
        this.board_count = board_count;
        this.created_date = created_date;
        this.updated_date = updated_date;
    }


}
