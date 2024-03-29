package shop.mtcoding.newblog.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.newblog.dto.ResponseDto;
import shop.mtcoding.newblog.dto.board.BoardReq.BoardSaveReqDto;
import shop.mtcoding.newblog.dto.board.BoardReq.BoardUpdateReqDto;
import shop.mtcoding.newblog.dto.board.BoardResp.BoardDetailRespDto;
import shop.mtcoding.newblog.dto.board.BoardResp.BoardListRespDto;
import shop.mtcoding.newblog.dto.reply.ReplyResp.ReplyDetailRespDto;
import shop.mtcoding.newblog.handler.ex.CustomApiException;
import shop.mtcoding.newblog.handler.ex.CustomException;
import shop.mtcoding.newblog.model.Board;
import shop.mtcoding.newblog.model.BoardRepository;
import shop.mtcoding.newblog.model.Love;
import shop.mtcoding.newblog.model.LoveRepository;
import shop.mtcoding.newblog.model.User;
import shop.mtcoding.newblog.service.BoardService;
import shop.mtcoding.newblog.service.ReplyService;
import shop.mtcoding.newblog.util.PagingVO;

@Controller
public class BoardController {
    @Autowired
    private HttpSession session;
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private LoveRepository loveRepository;

    @DeleteMapping("/board/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable int id) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 되지 않았습니다");
        }
        boardService.게시글삭제(id, principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "삭제성공", null), HttpStatus.OK);
    }

    @PutMapping("/board/{id}")
    public @ResponseBody ResponseEntity<?> update(@PathVariable int id,
            @RequestBody BoardUpdateReqDto boardUpdateReqDto, HttpServletResponse response) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 되지 않았습니다");
        }
        if (boardUpdateReqDto.getTitle() == null ||
                boardUpdateReqDto.getTitle().isEmpty()) {
            throw new CustomApiException("title을 작성해주세요");
        }

        if (boardUpdateReqDto.getContent() == null ||
                boardUpdateReqDto.getContent().isEmpty()) {
            throw new CustomApiException("content을 작성해주세요");
        }
        boardService.게시글수정(id, boardUpdateReqDto, principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "게시글수정 성공", null),
                HttpStatus.OK);
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
        Board boardPS = boardRepository.findById(id);
        if (boardPS == null) {
            throw new CustomException("없는 게시글을 수정할 수 없습니다.");
        }
        if (boardPS.getUserId() != principal.getId()) {
            throw new CustomException("게시글을 수정할 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        model.addAttribute("board", boardPS);
        return "board/updateForm";
    }

    @PostMapping("/board")
    public @ResponseBody ResponseEntity<?> save(@RequestBody BoardSaveReqDto BoardSaveReqDto) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
        if (BoardSaveReqDto.getTitle() == null || BoardSaveReqDto.getTitle().isEmpty()) {
            throw new CustomApiException("title을 작성해주세요");
        }
        if (BoardSaveReqDto.getContent() == null || BoardSaveReqDto.getContent().isEmpty()) {
            throw new CustomApiException("content를 작성해주세요");
        }
        if (BoardSaveReqDto.getTitle().length() > 100) {
            throw new CustomApiException("title의 길이가 100자 이하여야 합니다");
        }

        boardService.글쓰기(BoardSaveReqDto, principal.getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "게시글작성 성공", null), HttpStatus.OK);
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable int id, Model model) {

        User principal = (User) session.getAttribute("principal");
        if (principal != null) {
            Love love = loveRepository.findByBoardIdAndUserIdLove(id, principal.getId());
            model.addAttribute("love", love);
        }

        BoardDetailRespDto board = boardService.글상세보기(id);
        model.addAttribute("board", board);

        List<ReplyDetailRespDto> replyDtoList = replyService.getReplyList(board.getId());
        model.addAttribute("replyDtoList", replyDtoList);

        return "board/detail";
    }

    @GetMapping({ "/", "board" })
    public String main(Model model, BoardListRespDto boardListRespDto,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(defaultValue = "1", value = "nowPage", required = false) String nowPage,
            @RequestParam(value = "cntPerPage", required = false) String cntPerPage) {

        model.addAttribute("dtos", boardRepository.findAllWithUser());

        int total = boardRepository.countBoard(search);
        if (nowPage == null && cntPerPage == null) {
            nowPage = "1";
            cntPerPage = "8";
        } else if (nowPage == null) {
            nowPage = "1";
        } else if (cntPerPage == null) {
            cntPerPage = "8";
        }
        PagingVO vo = new PagingVO(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage));
        // Integer.parseInt(nowPage) 문자열 정수 변환
        model.addAttribute("paging", vo);

        List<BoardListRespDto> boardList = boardRepository.findAllWithPaging(vo, search);
        model.addAttribute("boardList", boardList);

        model.addAttribute("search", search);
        return "board/main";
    }
}
