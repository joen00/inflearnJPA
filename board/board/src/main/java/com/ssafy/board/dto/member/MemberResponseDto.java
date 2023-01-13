package com.ssafy.board.dto.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.board.entity.board.Board;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel(value = "MemberResponseDto : 멤버 정보", description = "멤버 상세 정보를 나타낸다.")
@Data
public class MemberResponseDto {

    @ApiParam(value = "멤버 id", required = true)
    private int member_id;
    @ApiParam(value = "닉네임", required = true)
    private String nickname;
    @ApiParam(value = "이메일", required = true)
    private String email;
    @ApiParam(value = "게시판 리스트", required = true)
    private List<Board> boardList = new ArrayList<>();

    // Board 자체 사용함 이게 맞나?
    @Builder
    public MemberResponseDto(int member_id, String nickname, String email, List<Board> boardList) {
        this.member_id = member_id;
        this.nickname = nickname;
        this.email = email;
        this.boardList = boardList.stream()
                .map(o -> new Board(o))
                .collect(Collectors.toList());
    }


}
