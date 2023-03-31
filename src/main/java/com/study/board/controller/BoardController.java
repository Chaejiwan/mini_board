package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public String root() throws Exception {

        return "redirect:/board/list";
    }

    @GetMapping("/board/write") //localhost:8081/board/write
    public String boardWriteForm() {
        return "boardWrite";
    }

    @PostMapping("/board/writePro")
    public String boardWritePro(Board board, Model model) {
        boardService.write(board);


        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model) {
        model.addAttribute("list", boardService.boardList());

        return "boardList";
    }

    @GetMapping("/board/view")
    public String boardView(Model model, Integer id) {
        model.addAttribute("board", boardService.boardView(id));

        return "boardView";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id) {

        boardService.boardDelete(id);

        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("board", boardService.boardView(id));

        return "boardModify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board) {

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp);

        return "redirect:/board/list";
    }
}
