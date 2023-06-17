package shop.mtcoding.newblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.newblog.dto.love.LoveReqDto;
import shop.mtcoding.newblog.handler.ex.CustomApiException;
import shop.mtcoding.newblog.model.Board;
import shop.mtcoding.newblog.model.BoardRepository;
import shop.mtcoding.newblog.model.Love;
import shop.mtcoding.newblog.model.LoveRepository;

@Service
public class LoveService {

    @Autowired
    private LoveRepository loveRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void 좋아요취소(int id, int principalId) {
        Love lovePS = loveRepository.findById(id);

        if (lovePS == null) {
            throw new CustomApiException("좋아요 내역이 존재하지 않습니다", HttpStatus.BAD_REQUEST);
        }

        System.out.println("테스트 삭제 love 유저아이디: " + lovePS.getUserId());
        System.out.println("테스트 삭제 love 아이디: " + lovePS.getId());
        if (lovePS.getUserId() != principalId) {
            throw new CustomApiException("좋아요 취소 권한이 없습니다.", HttpStatus.BAD_REQUEST);
        }
        try {
            loveRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 문제가 생겼습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Transactional
    public int 좋아요(int boardId, int principalId) {

        Board boardOP = boardRepository.findById(boardId);
        if (boardOP == null) {
            throw new CustomApiException("게시글이 존재하지 않습니다", HttpStatus.BAD_REQUEST);
        }

        Love love = new Love();
        love.setBoardId(boardId);
        love.setUserId(principalId);
        loveRepository.insert(love);

        return love.getId();

    }
}
