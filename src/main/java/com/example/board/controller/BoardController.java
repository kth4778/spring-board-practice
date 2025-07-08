package com.example.board.controller;

import com.example.board.domain.Board;
import com.example.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "게시판 API", description = "게시글 CRUD(생성, 조회, 수정, 삭제) 기능을 제공합니다.")
@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @Operation(
            summary = "게시글 등록",
            description = "새로운 게시글을 등록합니다.<br>title, content, author를 모두 입력해야 합니다."
    )
    @PostMapping
    public Board create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "게시글 등록 정보", required = true)
            @RequestBody Board board
    ) {
        return boardService.save(board);
    }

    @Operation(
            summary = "게시글 전체 목록 조회",
            description = "등록된 모든 게시글을 리스트로 반환합니다."
    )
    @GetMapping
    public List<Board> list() {
        return boardService.findAll();
    }

    @Operation(
            summary = "게시글 단건 조회",
            description = "특정 id의 게시글 1건을 조회합니다.<br>존재하지 않을 경우 404 NOT FOUND를 반환합니다."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Board> get(
            @Parameter(description = "게시글 id (PK)", example = "1", required = true)
            @PathVariable Long id
    ) {
        return boardService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "게시글 수정",
            description = "특정 id의 게시글을 수정합니다.<br>전체 항목(title, content, author) 값을 모두 보내야 정상적으로 동작합니다."
    )
    @PutMapping("/{id}")
    public ResponseEntity<Board> update(
            @Parameter(description = "수정할 게시글 id (PK)", example = "1", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "수정할 게시글 정보", required = true)
            @RequestBody Board board
    ) {
        return boardService.findById(id)
                .map(existing -> {
                    existing.setTitle(board.getTitle());
                    existing.setContent(board.getContent());
                    existing.setAuthor(board.getAuthor());
                    return ResponseEntity.ok(boardService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "게시글 삭제",
            description = "특정 id의 게시글을 삭제합니다.<br>삭제 성공시 204 No Content를 반환합니다."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "삭제할 게시글 id (PK)", example = "1", required = true)
            @PathVariable Long id
    ) {
        boardService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
