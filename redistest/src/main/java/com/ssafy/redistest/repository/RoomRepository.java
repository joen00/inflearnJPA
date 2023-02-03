package com.ssafy.redistest.repository;


import com.ssafy.redistest.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;

@RequiredArgsConstructor
@Repository
public class RoomRepository {


    private final RedisTemplate redisTemplate;


    private static final String TO_ROOMS = "TO_ROOMS"; // 룸 저장
    public static final String ENTER_INFO = "ENTER_INFO"; // 룸에 입장한 사용자의 sessionId(사용자 id)와 룸 id를 맵핑한 정보 저장
    public static final String USER_COUNT = "USER_COUNT"; // 룸에 입장한 클라이언트수 저장
    public static final String USER_INFO = "USER_INFO"; // 룸에 입장한 클라이언트수 저장


    // HashOperations<table, dto key, dto 객체>
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Room> hashOpsChatRoom;  // 방 ("TO_ROOM", 방 id, room 객체)
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, UserInfo> hashOpsEnterInfo;  // 방 유저 정보("ENTER_INFO", 세션 id (유저 id), 유저 객체)
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOps;  // 미팅방 유저수
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Room> hashOpsUser;


    // 모든 미팅방 조회
    public List<Room> findAllRoom() {
        return hashOpsChatRoom.values(TO_ROOMS);
    }

    public List<UserInfo> findAllUser() {
        return hashOpsEnterInfo.values(ENTER_INFO);
    }

    // 특정 채팅방 조회
    public Room findRoomById(String id) {
        return hashOpsChatRoom.get(TO_ROOMS, id);
    }

    // 채방 생성 : 서버간 미팅방 공유를 위해 redis hash에 저장한다.
    public Room createChatRoom(RoomRequest dto) {
        Room room = Room.create(dto.getTitle(), dto.getContent(), dto.getHostId(), dto.getHostNickname()); // dto에서 함수를 만듬 (update 같이)
        hashOpsChatRoom.put(TO_ROOMS, room.getRoomId(), room );

        // 유저 넣어줌
        UserInfo userInfo = new UserInfo();
        userInfo.setRoomId(room.getRoomId());
        userInfo.setUserId(dto.getHostId());
        userInfo.setNickname(dto.getHostNickname());
        hashOpsEnterInfo.put(ENTER_INFO, dto.getHostId(), userInfo);
        return room ;
    }

    // 방 삭제 : redis hash에 저장된 미팅방 파괴
    public void deleteRoom(String roomId) {
        // 방 정보 삭제
        hashOpsChatRoom.delete(TO_ROOMS, roomId);

        // hashOpsEnterInfo(미팅방 유저 정보)에서 미팅방 ID를 value으로 가지는 key를 삭제
        Map<String, UserInfo> entries = hashOpsEnterInfo.entries(ENTER_INFO);
        for (Map.Entry<String, UserInfo> stringStringEntry : entries.entrySet()) { // 매핑된 정보를 전체 출력 == entrySet
            if (stringStringEntry.getValue().getRoomId().equals(roomId)) { // roomID랑
                hashOpsEnterInfo.delete(ENTER_INFO, stringStringEntry.getKey()); //
            }
        }
    }

    // 유저가 입장한 미팅방ID와 유저 세션ID 맵핑 정보 저장 HashOperations<table, dto key, dto 객체>
    public void setUserEnterInfo(String sessionId, UserInfo userInfo) {

        hashOpsEnterInfo.put(ENTER_INFO, sessionId, userInfo); // 유저와 방 관련 매핑
        
        // 유저 userlist에 등록하기
        Room room1 = hashOpsChatRoom.get(TO_ROOMS, userInfo.getRoomId());
        System.out.println(room1.getUserList().toString() + " ----------------- ");
        List<UserResponse> newList = room1.getUserList();
        UserResponse userResponse = new UserResponse(userInfo.getUserId(), userInfo.getNickname());

        newList.add(userResponse);
        room1.setUserList(newList);
        room1.setUserCount(room1.getUserList().size());
        hashOpsChatRoom.put(TO_ROOMS, room1.getRoomId(), room1 );
    }

    // 유저 세션으로 입장해 있는 미팅방 ID 조회
    public UserInfo getUserEnterRoomId(String sessionId) {
        return hashOpsEnterInfo.get(ENTER_INFO, sessionId);
    }

    // 유저 세션정보와 맵핑된 미팅방ID 삭제
    public void removeUserEnterInfo(String sessionId, UserInfo userInfo) {

        Room room1 = hashOpsChatRoom.get(TO_ROOMS, userInfo.getRoomId());
        if (sessionId.equals(room1.getHostId())){
            this.deleteRoom(room1.getRoomId());
            return;
        }
        hashOpsEnterInfo.delete(ENTER_INFO, sessionId);

        // 유저 list에 저장
        List<UserResponse> newList = room1.getUserList();
        UserResponse userResponse = new UserResponse(userInfo.getUserId(), userInfo.getNickname());
        newList.remove(userResponse);

        room1.setUserList(newList);
        room1.setUserCount(room1.getUserList().size());
        hashOpsChatRoom.put(TO_ROOMS, room1.getRoomId(), room1 );
    }




    ////////////////////
//    public long plusUserCount(String roomId, Room room) {
//        long count = room.getUserCount() + 1;
//        room.setUserCount(count);
//        hashOpsChatRoom.put(TO_ROOMS, roomId, room );
//
//        return count;
//    }
//
//    public long minusUserCount(String roomId, Room room) {
//        long count = room.getUserCount() - 1;
//        room.setUserCount(count);
//        hashOpsChatRoom.put(TO_ROOMS, roomId, room );
//
//        return count;
//    }


}
