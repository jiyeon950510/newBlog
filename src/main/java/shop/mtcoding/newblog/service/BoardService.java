package shop.mtcoding.newblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.newblog.dto.board.BoardReq.BoardSaveReqDto;
import shop.mtcoding.newblog.dto.board.BoardReq.BoardUpdateReqDto;
import shop.mtcoding.newblog.dto.board.BoardResp.BoardDetailRespDto;
import shop.mtcoding.newblog.handler.ex.CustomApiException;
import shop.mtcoding.newblog.model.Board;
import shop.mtcoding.newblog.model.BoardRepository;
import shop.mtcoding.newblog.util.HtmlParser;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void 글쓰기(BoardSaveReqDto boardSaveReqDto, int userId) {

        String thumbnail = HtmlParser.getThumbnail(boardSaveReqDto.getContent());

        int result = boardRepository.insert(
                boardSaveReqDto.getTitle(), boardSaveReqDto.getContent(), userId, thumbnail);
        if (result != 1) {
            throw new CustomApiException("글쓰기 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Transactional
    public void 게시글삭제(int id, int userId) {
        Board boardPS = boardRepository.findById(id);
        if (boardPS == null) {
            throw new CustomApiException("없는 게시글을 삭제할 수 없습니다.");
        }
        if (boardPS.getUserId() != userId) {
            throw new CustomApiException("해당 게시글을 삭제할 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        try {
            boardRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 문제가 생겼습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void 게시글수정(int id, BoardUpdateReqDto boardUpdateReqDto, int principalId) {
        Board boardPS = boardRepository.findById(id);
        if (boardPS == null) {
            throw new CustomApiException("해당 게시글을 찾을 수 없습니다.");
        }
        if (boardPS.getUserId() != principalId) {
            throw new CustomApiException("게시글을 수정할 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        String thumbnail = HtmlParser.getThumbnail(boardUpdateReqDto.getContent());
        int result = boardRepository.updateById(id, boardUpdateReqDto,
                thumbnail);
        if (result != 1) {
            throw new CustomApiException("게시글 수정에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public BoardDetailRespDto 글상세보기(int id) {
        BoardDetailRespDto boardDto = boardRepository.findByIdWithUser(id);
        if (boardDto == null) {
            throw new CustomApiException("존재하지 않는 게시물 입니다");
        }
        return boardDto;
    }

}
