package com.ssafy.redistest.service;

import com.ssafy.redistest.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoomService {

    private final RedisTemplate redisTemplate;
    private final RoomRepository roomRepository;



}
