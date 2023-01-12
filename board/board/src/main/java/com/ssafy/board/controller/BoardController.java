package com.ssafy.board.controller;

import com.ssafy.board.dto.board.BoardDto;
import com.ssafy.board.entity.board.Board;
import com.ssafy.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;

//    @GetMapping("/board")
//    public List<BoardDto> read(){
//        List<Board> boards = boardRepository.findAll();
//        List<BoardDto> result = boards.stream()
//                .map(o -> new BoardDto(o))
//                .collect(Collectors.toList());
//        return result;
//    }
}
