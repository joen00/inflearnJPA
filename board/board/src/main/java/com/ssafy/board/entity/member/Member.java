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
    // 만약에 안써주면 무한 로프가 돌면서 StackOverflowError: null 에러가 같이 난다.
    @JsonIgnore                     // 즉 여기에는 값을 넣을 수 없다.!!! 즉, 외래키가 있는 곳을 주인으로 해라 = 다 쪽이 주인(board)
    @OneToMany(mappedBy = "member") // 연관관계에서 누가 리더인지 // 이것을 쓴 곳에서가 주인이라 따라서 여기는 그냥 조회만 가능하고 주인인 곳에서 수정할 수 있다.
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
