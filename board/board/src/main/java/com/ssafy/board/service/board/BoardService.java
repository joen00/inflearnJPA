package com.ssafy.board.service.board;

import com.ssafy.board.dto.board.BoardRequestDto;
import com.ssafy.board.dto.board.BoardResponseDto;
import com.ssafy.board.entity.board.Board;
import com.ssafy.board.entity.member.Member;
import com.ssafy.board.repository.BoardRepository;
import com.ssafy.board.repository.MemberRepository;
import com.ssafy.board.service.member.MemberService;
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
    private final MemberRepository memberRepository;

    @Transactional
    public boolean savePost (BoardRequestDto boardRequestDto){
//        Board board = boardRequestDto.ToEntity();
//        System.out.println(board.getMember().getMember_id());
//        Optional<Member> member = memberRepository.findById(boardRequestDto.getMember().getMember_id());
//        if (member.get().getMember_id() == 0)
//            return false;
        Board board = boardRepository.save(boardRequestDto.ToEntity());
        if (board == null){
            return false;
        }else return true;
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
                    .created_date(board.getCreated_date())
                    .updated_date(board.getUpdated_date())
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
