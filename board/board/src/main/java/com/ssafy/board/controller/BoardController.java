package com.ssafy.board.controller;


import com.ssafy.board.dto.board.BoardRequestDto;
import com.ssafy.board.dto.board.BoardResponseDto;
import com.ssafy.board.dto.member.MemberRequestDto;
import com.ssafy.board.entity.board.Board;
import com.ssafy.board.repository.BoardRepository;
import com.ssafy.board.service.board.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Api("게시판 컨트롤러 API V1")
public class BoardController {

    private final BoardService boardService;

    @ApiOperation(value = "게시판 작성", notes = "게시판 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PostMapping("/board")
    public  ResponseEntity<String> savePost(@RequestBody BoardRequestDto request) {
        boolean result = boardService.savePost(request);
        if (result)
            return new ResponseEntity<String>("success", HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "게시판 리스트 전체 조회", notes = "성공하면 게시판 리스트 전체의 값이 나온다.", response = String.class)
    @GetMapping("/board")
    public ResponseEntity<?> getBoardList(){
        return new ResponseEntity(boardService.getBoardList(), HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "게시판 삭제", notes = "성공하면 success.", response = String.class)
    @DeleteMapping("/board/{id}")
    public ResponseEntity<?> delete(@ApiParam(value = "BoardDto", required = true) @PathVariable("id") int id){
        boardService.deletePost(id);
        return new ResponseEntity("success", HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "게시판 수정", notes = "게시글의 id값을 받고 수정한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PutMapping("/board/{id}")
    public ResponseEntity<?> updatePost(@ApiParam(value = "BoardDto", required = true) @PathVariable("id") int  id,
                                        @ApiParam(value = "BoardDto", required = true) @RequestBody BoardRequestDto request){
        boolean result = boardService.update(id, request);
        if (result)
            return new ResponseEntity<String>("success", HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
    }

}
