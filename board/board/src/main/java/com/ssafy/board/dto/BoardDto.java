package com.ssafy.board.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.board.entity.Board;
import com.ssafy.board.entity.Member;
import com.ssafy.board.repository.BoardRepository;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
public class BoardDto {

    private int board_id;
    @JsonIgnore
    private Member member;
    private String board_title;
    private String board_writer;
    private String board_content;
    private int board_count;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;

    public BoardDto (Board board) {
        this.board_id = board.getBoard_id();
        this.member = board.getMember();
        this.board_title = board.getBoard_title();
        this.board_content = board.getBoard_content();
        this.board_writer = board.getBoard_writer();
        this.board_count = board.getBoard_count();
        this.created_date = board.getCreated_date();
        this.updated_date = board.getUpdated_date();
    }

}
