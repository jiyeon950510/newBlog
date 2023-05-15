package shop.mtcoding.newblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.newblog.dto.reply.ReplyResp.ReplyDetailRespDto;
import shop.mtcoding.newblog.handler.ex.CustomApiException;
import shop.mtcoding.newblog.model.Board;
import shop.mtcoding.newblog.model.BoardRepository;
import shop.mtcoding.newblog.model.Reply;
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
        try {
            replyRepository.insert(comment, boardId, userId);
        } catch (Exception e) {
            throw new CustomApiException("댓글 작성 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<ReplyDetailRespDto> getReplyList(Integer boardId) {
        List<ReplyDetailRespDto> dtoList = replyRepository.findByBoardId(boardId);
        return dtoList;
    }

    @Transactional
    public void 댓글삭제(int replyId, int userId) {
        Reply replyPS = replyRepository.findById(replyId);
        if (replyPS == null) {
            throw new CustomApiException("존재하지 않는 댓글 입니다", HttpStatus.FORBIDDEN);
        }
        if (replyPS.getUserId() != userId) {
            throw new CustomApiException("댓글삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        try {
            replyRepository.deleteById(replyId);
        } catch (Exception e) {
            throw new CustomApiException("댓글 삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
