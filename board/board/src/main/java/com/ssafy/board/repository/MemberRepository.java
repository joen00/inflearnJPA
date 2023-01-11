package com.ssafy.board.repository;

import com.ssafy.board.entity.Board;
import com.ssafy.board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
