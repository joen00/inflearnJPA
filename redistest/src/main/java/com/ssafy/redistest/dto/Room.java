package com.ssafy.redistest.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.*;


/**
 * REDIS 저장 채팅방
 * key: roomID
 */
@Data
public class Room implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private String roomId;
    private String title;
    private String content;
    private String hostId;
    private String hostNickname;
    private long userCount;

    private List<UserResponse> userList = new ArrayList<>(); // id, nickname

    public static Room create(String title, String content, String hostId, String hostNickname) {
        Room room = new Room();
        room.roomId = UUID.randomUUID().toString();
        room.title = title;
        room.content = content;
        room.hostId = hostId;
        room.hostNickname = hostNickname;
        room.userCount = 1;
        UserResponse userResponse = new UserResponse(hostId, hostNickname);
        room.userList.add(userResponse);
        return room;
    }

}
