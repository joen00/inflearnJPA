package com.ssafy.board.entity.board;

import com.ssafy.board.entity.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue
    private int board_id;

    @ManyToOne(fetch = FetchType.LAZY) // xtoOne으로 끝나면 fetch를 해준다.
    @JoinColumn(name = "member_id")
    private Member member;

    private String board_title;
    private String board_writer;
    private String board_content;
    private int board_count;

    @CreatedDate
    private LocalDateTime created_date;

    @LastModifiedDate
    private LocalDateTime updated_date;


    public static Board createBoard(Member member, String board_title, String board_content) {
        Board board = new Board();
        board.board_title = board_title;
        board.board_content = board_content;
        board.board_writer = member.getNickname();
        board.changeMember(member); //
        board.created_date = LocalDateTime.now();
        board.updated_date = LocalDateTime.now();
        return board;
    }

    // 양방향관계 => 무한 루프돌아선
    public void changeMember(Member member){
        this.member = member; // 현재 board에 있는 member
        member.getBoardList().add(this); // member에 있는 boardlist에 넣어줌
    }

}
