package shop.mtcoding.newblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.newblog.dto.user.UserReq.JoinReqDto;
import shop.mtcoding.newblog.handler.ex.CustomException;
import shop.mtcoding.newblog.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @PostMapping("/join")
    public String join(JoinReqDto joinReqDto) {

        if (joinReqDto.getUsername() == null || joinReqDto.getUsername().isEmpty()) {
            throw new CustomException("username을 작성해주세요");
        }

        if (joinReqDto.getPassword() == null || joinReqDto.getPassword().isEmpty()) {
            throw new CustomException("password을 작성해주세요");
        }

        if (joinReqDto.getEmail() == null || joinReqDto.getEmail().isEmpty()) {
            throw new CustomException("email을 작성해주세요");
        }

        int result = userService.회원가입(joinReqDto);
        if (result != 1) {
            throw new CustomException("회원가입실패");
        }

        return "redirect:/loginForm";
    }

    @GetMapping("joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

}
