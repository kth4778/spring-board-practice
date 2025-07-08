package com.example.board.repository;

import com.example.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<엔티티타입, PK타입> 상속받으면, CRUD 메서드가 자동생성됩니다.
public interface BoardRepository extends JpaRepository<Board, Long> {
    // 별도 메서드 없이도 기본 CRUD 사용 가능
}
