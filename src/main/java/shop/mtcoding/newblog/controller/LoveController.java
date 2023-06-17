package shop.mtcoding.newblog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.newblog.dto.ResponseDto;
import shop.mtcoding.newblog.dto.love.LoveReqDto;
import shop.mtcoding.newblog.handler.ex.CustomApiException;
import shop.mtcoding.newblog.model.User;
import shop.mtcoding.newblog.service.LoveService;

@Controller
public class LoveController {

    @Autowired
    private LoveService loveService;

    @Autowired
    private HttpSession session;

    @DeleteMapping("/love/{id}")
    public @ResponseBody ResponseEntity<?> loveDelete(@PathVariable int id) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 필요합니다.", HttpStatus.BAD_REQUEST);
        }
        loveService.좋아요취소(id, principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "좋아요 취소 성공", null), HttpStatus.OK);
    }

    @PostMapping("/love")
    public @ResponseBody ResponseEntity<?> loveSave(@RequestBody LoveReqDto loveReqDto) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 필요합니다.", HttpStatus.BAD_REQUEST);
        }
        if (loveReqDto.getBoardId() == null) {
            throw new CustomApiException("boardId를 전달해 주세요");
        }
        int loveId = loveService.좋아요(loveReqDto.getBoardId(), principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "좋아요 성공", loveId), HttpStatus.OK);
    }

}
