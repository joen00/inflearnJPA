package com.ssafy.board.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int member_id;
    private String nickname;
    private String email;

    @OneToMany(mappedBy = "member") // 연관관계에서 누가 리더인지
    private List<Board> boardList = new ArrayList<>();

    public Member(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }


}
