package shop.mtcoding.newblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.newblog.handler.ex.CustomApiException;
import shop.mtcoding.newblog.model.Board;
import shop.mtcoding.newblog.model.BoardRepository;
import shop.mtcoding.newblog.model.ReplyRepository;

@Service
public class ReplyService {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public void save(String comment, int boardId, int userId) {
        Board boardPS = boardRepository.findById(userId);
        if (boardPS == null) {
            throw new CustomApiException("존재하지 않는 게시물 입니다");
        }
        replyRepository.insert(comment, boardId, userId);
    }

}
