package com.ssafy.redistest.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.UUID;


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
    private String host;
    private long userCount;

    public static Room create(String title, String content, String host) {
        Room room = new Room();
        room.roomId = UUID.randomUUID().toString();
        room.title = title;
        room.content = content;
        room.host = host;
        return room;
    }

}
