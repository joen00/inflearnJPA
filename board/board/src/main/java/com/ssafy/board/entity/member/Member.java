package com.ssafy.board.entity.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.board.entity.TimeEntity;
import com.ssafy.board.entity.board.Board;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto 일때 사용
    private int member_id;
    private String nickname;
    private String email;

    // @JsonIgnore // JSON으로 표현해줄때 제외한다
    @JsonIgnore
    @OneToMany(mappedBy = "member") // 연관관계에서 누가 리더인지
    private List<Board> boardList = new ArrayList<>();

    @Builder
    public Member(int member_id, String nickname, String email, List<Board> boardList) {
        this.member_id = member_id;
        this.nickname = nickname;
        this.email = email;
        this.boardList = boardList;
    }

    public boolean updateMember(String nickname, String email){
        this.nickname = nickname;
        this.email = email;
        return true;
    }

}
