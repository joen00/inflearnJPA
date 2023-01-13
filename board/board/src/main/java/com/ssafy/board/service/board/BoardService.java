package com.ssafy.board.service.board;

import com.ssafy.board.dto.board.BoardRequestDto;
import com.ssafy.board.dto.board.BoardResponseDto;
import com.ssafy.board.entity.board.Board;
import com.ssafy.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void savePost (BoardRequestDto boardRequestDto){
//        Board board = boardRequestDto.ToEntity();
//        System.out.println(board.getMember().getMember_id());
        boardRepository.save(boardRequestDto.ToEntity());
    }

    @Transactional
    public List<BoardResponseDto> getBoardList(){
        List<Board> all = boardRepository.findAll();
        List<BoardResponseDto> boardDtoList = new ArrayList<>();

        for(Board board : all){
            BoardResponseDto boardDto = BoardResponseDto.builder()
                    .member(board.getMember())
                    .board_id(board.getBoard_id())
                    .board_title(board.getBoard_title())
                    .board_content(board.getBoard_content())
                    .board_count(board.getBoard_count())
                    .board_writer(board.getBoard_writer())
                    .build();
            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }

    @Transactional
    public void deletePost(int id){
        boardRepository.deleteById(id);
    }

    @Transactional
    public boolean update(int id, BoardRequestDto dto){
        Optional<Board> byId = boardRepository.findById(id);
        Board board = byId.get();
        return board.updateBoard(dto.getBoard_title(), dto.getBoard_content());
    }

}
