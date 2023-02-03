package com.ssafy.redistest.controller;


import com.ssafy.redistest.dto.Room;
import com.ssafy.redistest.dto.RoomRequest;
import com.ssafy.redistest.dto.UserInfo;
import com.ssafy.redistest.dto.UserListInfo;
import com.ssafy.redistest.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/together")
public class RoomController {

    private final RoomRepository roomRepository;

    @GetMapping("/room")
    public ResponseEntity room() {
        List<Room> chatRooms = roomRepository.findAllRoom();
        // chatRooms.stream().forEach(room -> room.setUserCount(roomRepository.getUserCount(room.getRoomId()))); // 이거 안됨 = getUserCount
        return ResponseEntity.ok().body(chatRooms);
    }

    @PostMapping("/room")
    public Room createRoom(@RequestBody RoomRequest dto) {
        //System.out.println("여기" + dto.getContent());
        return roomRepository.createChatRoom(dto);
    }

    // 채팅방 파괴
    @DeleteMapping("/room/{roomId}") // 방이 삭제 되면 자동으로 유저들도 삭제 되는거 확인함
    public ResponseEntity deleteRoom(@PathVariable String roomId) {
        roomRepository.deleteRoom(roomId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/room/user")
    public ResponseEntity findAllUser() {
        List<UserInfo> userInfos = roomRepository.findAllUser(); // 방과 유저 정보 반환 -> 방이 삭제 되면 자동으로 유저들도 삭제 되는거 확인함
        return ResponseEntity.ok().body(userInfos); 
    }

    @GetMapping("/room/userlist/{roomId}")
    public ResponseEntity roomUserList(@PathVariable String roomId) {
        UserListInfo userListInfo = roomRepository.findUserList(roomId);


        return ResponseEntity.ok().body(userListInfo);
    }

    // 방 입장
    @PostMapping("/room/enter")
    public ResponseEntity enterRoom(@RequestBody UserInfo userInfo){ // 방 들어갈때 유저 count 됨
        roomRepository.setUserEnterInfo(userInfo.getUserId(), userInfo);
        // roomRepository.plusUserCount(userInfo.getRoomId()); // 이게 안됨
        // Room room = roomRepository.findRoomById(userInfo.getRoomId());
        // System.out.println(room.getRoomId());
        // roomRepository.plusUserCount(userInfo.getRoomId(), room);
//        System.out.println(roomRepository.getUserCount(userInfo.getRoomId()));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    // 방 나감
    @PostMapping("/room/out")
    public ResponseEntity outRoom(@RequestBody UserInfo userInfo){ // 방 나갈때 유저 count 됨
        roomRepository.removeUserEnterInfo(userInfo.getUserId(), userInfo);
        // roomRepository.plusUserCount(userInfo.getRoomId()); // 이게 안됨
        // Room room = roomRepository.findRoomById(userInfo.getRoomId());
        // System.out.println(room.getRoomId());
        // roomRepository.minusUserCount(userInfo.getRoomId(), room);
//        System.out.println(roomRepository.getUserCount(userInfo.getRoomId())); // 이거 0으로 나옴

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }


}
