package com.ssafy.board.controller;


import com.ssafy.board.dto.board.BoardRequestDto;
import com.ssafy.board.dto.board.BoardResponseDto;
import com.ssafy.board.dto.member.MemberRequestDto;
import com.ssafy.board.entity.board.Board;
import com.ssafy.board.repository.BoardRepository;
import com.ssafy.board.service.board.BoardService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board")
    public void savePost(@RequestBody BoardRequestDto request) {
        boardService.savePost(request);
    }

    @GetMapping("/board")
    public ResponseEntity<?> getBoardList(){
        return new ResponseEntity(boardService.getBoardList(), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<?> delete(@ApiParam(value = "BoardDto", required = true) @PathVariable("id") int id){
        boardService.deletePost(id);
        return new ResponseEntity("success", HttpStatus.ACCEPTED);
    }

    @PutMapping("/board/{id}")
    public ResponseEntity<?> updatePost(@ApiParam(value = "BoardDto", required = true) @PathVariable("id") int  id,
                                        @ApiParam(value = "BoardDto", required = true) @RequestBody BoardRequestDto request){
        // memberService.update(id, request);
        boolean result = boardService.update(id, request);
        if (result)
            return new ResponseEntity<String>("success", HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
    }

}
