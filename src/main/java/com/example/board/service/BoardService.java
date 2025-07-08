package com.example.board.service;

import com.example.board.domain.Board;
import com.example.board.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    // 생성자를 통해 BoardRepository 주입 (의존성 주입)
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 게시글 저장
    public Board save(Board board) {
        return boardRepository.save(board);
    }

    // 전체 게시글 목록 반환
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    // id로 게시글 1개 찾기
    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

    // id로 게시글 삭제
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }
}
