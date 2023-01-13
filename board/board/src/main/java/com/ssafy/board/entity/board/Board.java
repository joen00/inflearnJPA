package com.ssafy.board.entity.board;

import com.ssafy.board.entity.TimeEntity;
import com.ssafy.board.entity.member.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int board_id;
    @ManyToOne(fetch = FetchType.LAZY) // xtoOne으로 끝나면 fetch를 해준다.
    @JoinColumn(name = "member_id")
    private Member member;
    private String board_title;
    private String board_writer;
    private String board_content;
    private int board_count;

    @Builder
    public Board(int board_id, Member member, String board_title, String board_writer, String board_content, int board_count) {
        this.board_id = board_id;
        this.member = member;
        this.board_title = board_title;
        this.board_writer = board_writer;
        this.board_content = board_content;
        this.board_count = board_count;
    }


    // 안쓸 수 있는 방향으로
    public Board(Board o) {
        this.member = o.member;
        this.board_id = o.board_id;
        this.board_title = o.board_title;
        this.board_writer = o.board_writer;
        this.board_content = o.board_content;
        this.board_count = o.board_count;
    }


//    public static Board createBoard(Member member, String board_title, String board_content) {
//        Board board = new Board();
//        board.board_title = board_title;
//        board.board_content = board_content;
//        board.board_writer = member.getNickname();
//        board.changeMember(member); //
//        return board;
//    }
//
//    public void changeMember(Member member){
//        this.member = member; // 현재 board에 있는 member
//        member.getBoardList().add(this); // member에 있는 boardlist에 넣어줌
//    }

    public boolean updateBoard(String boardTitle, String boardContent) {
        this.board_title = boardTitle;
        this.board_content = boardContent;
        return true;
    }
}
