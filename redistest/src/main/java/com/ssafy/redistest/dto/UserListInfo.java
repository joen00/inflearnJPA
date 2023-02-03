package com.ssafy.redistest.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserListInfo implements Serializable {

    private String roomId; // 방번호
    private List<String> userList;

}
