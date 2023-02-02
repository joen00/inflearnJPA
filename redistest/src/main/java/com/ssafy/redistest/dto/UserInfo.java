package com.ssafy.redistest.dto;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * REDIS 저장 채팅방 유저 정보
 * key: sessionID
 */
@Data
public class UserInfo implements Serializable {


    private String roomId; // 방번호
    private String userId; // 유저 번호


//    public UserInfo(String roomId, String userId) {
//        this.roomId = roomId;
//        this.userId = userId;
//    }

//    public UserInfo() {
//
//    }


    public static UserInfo create(String roomId, String userId) {
        UserInfo userInfo = new UserInfo();
        userInfo.roomId = roomId;
        userInfo.userId = userId;
        return userInfo;
    }

}
