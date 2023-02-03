package com.ssafy.redistest.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserResponse implements Serializable {

    private String userId; // 유저 번호
    private String nickname;

    public UserResponse(String userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
    }
}
