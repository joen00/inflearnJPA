package com.ssafy.board.dto.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.board.dto.board.BoardRequestDto;
import com.ssafy.board.entity.board.Board;
import com.ssafy.board.entity.member.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@NoArgsConstructor
@Getter
@ApiModel(value = "MemberRequestDto : 멤버 정보", description = "멤버 상세 정보를 나타낸다.")
public class MemberRequestDto {

    @ApiParam(value = "멤버 id", required = true)
    private int member_id;
    @ApiParam(value = "닉네임", required = true)
    private String nickname;
    @ApiParam(value = "이메일", required = true)
    private String email;

    // 요청하는 것
    // Board 자체 사용함 -> 이게 맞나?
    @Builder
    public MemberRequestDto(int member_id, String nickname, String email) {
        this.member_id = member_id;
        this.nickname = nickname;
        this.email = email;
    }

    public Member ToEntity(){
        return Member.builder()
                .member_id(member_id)
                .nickname(nickname)
                .email(email)
                .build();
    }

}
