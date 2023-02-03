package com.ssafy.redistest.dto;

import lombok.Data;

@Data
public class RoomRequest {

    private String title;
    private String content;
    private String hostId;
    private String hostNickname;

}
