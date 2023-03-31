package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public void write(Board board, MultipartFile file) throws Exception {

        String projectPath = System.getProperty("user.dir") + "/src/main/webapp/"; // 저장할 경로
        UUID uuid = UUID.randomUUID(); // 랜덤 uuid 식별자 생성
        String fileName = uuid + "_" + file.getOriginalFilename(); // 파일 이름 설정
        File saveFile = new File(projectPath, fileName); // 파일 저장
        file.transferTo(saveFile); //파일 이동

        board.setFilename(fileName);
        board.setFilepath("/webapp/" + fileName);

        boardRepository.save(board);
    }

    public Page<Board> boardList(Pageable pageable) {

        return boardRepository.findAll(pageable);
    }

    public Board boardView(Integer id) {

        return boardRepository.findById(id).get();
    }

    public void boardDelete(Integer id) {

        boardRepository.deleteById(id);
    }

    public Page<Board> boardSearchList(String searchKeyWord, Pageable pageable) {
        return boardRepository.findByTitleContaining(searchKeyWord, pageable);
    }
}
