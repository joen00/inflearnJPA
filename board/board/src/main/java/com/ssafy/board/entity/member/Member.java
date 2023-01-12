package com.ssafy.board.entity.member;

import com.ssafy.board.entity.TimeEntity;
import com.ssafy.board.entity.board.Board;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto 일때 사용
    private int member_id;
    private String nickname;
    private String email;

    @OneToMany(mappedBy = "member") // 연관관계에서 누가 리더인지
    private List<Board> boardList = new ArrayList<>();

    @Builder
    public Member(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }


}
