package shop.mtcoding.newblog.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.h2.engine.SysProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import shop.mtcoding.newblog.dto.ResponseDto;
import shop.mtcoding.newblog.dto.user.UserReq.JoinReqDto;
import shop.mtcoding.newblog.dto.user.UserReq.LoginReqDto;
import shop.mtcoding.newblog.dto.user.UserReq.UpdateReqDto;
import shop.mtcoding.newblog.handler.ex.CustomApiException;
import shop.mtcoding.newblog.handler.ex.CustomException;
import shop.mtcoding.newblog.model.User;
import shop.mtcoding.newblog.model.UserRepository;
import shop.mtcoding.newblog.service.UserService;

@Controller
public class UserController {

    @Autowired
    private HttpSession session;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user/update")
    public String update(UpdateReqDto updateReqDto) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("잘못된 접근입니다. 로그인 후 이용해주세요", HttpStatus.UNAUTHORIZED);
        }
        if (updateReqDto.getUsername() == null || updateReqDto.getUsername().isEmpty()) {
            throw new CustomException("username을 작성해주세요");
        }

        if (updateReqDto.getPassword() == null || updateReqDto.getPassword().isEmpty()) {
            throw new CustomException("password을 작성해주세요");
        }
        if (updateReqDto.getEmail() == null || updateReqDto.getEmail().isEmpty()) {
            throw new CustomException("email을 작성해주세요");
        }

        userService.회원정보수정(updateReqDto, principal.getId());

        return "redirect:/user/updateForm";
    }

    @PostMapping("/user/profileUpdate")
    public String profileUpdate(MultipartFile profile) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("잘못된 접근입니다. 로그인 후 이용해주세요", HttpStatus.UNAUTHORIZED);
        }

        if (profile.isEmpty()) {
            throw new CustomException("사진이 전송되지 않았습니다.");
        }

        // 사진이 아니면 Exception 터트리기
        if (!profile.getContentType().startsWith("image")) {
            throw new CustomException("이미지 파일만 등록이 가능합니다.");
        }
        User userPS = userService.프로필사진수정(principal.getId(), profile);
        session.setAttribute("principal", userPS);

        return "redirect:/";
    }

    @GetMapping("/user/updateForm")
    public String updateForm(Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("잘못된 접근입니다. 로그인 후 이용해주세요", HttpStatus.UNAUTHORIZED);
        }
        User userPS = userRepository.findById(principal.getId());
        model.addAttribute("userPS", userPS);

        return "user/updateForm";
    }

    @PostMapping("/login")
    public String login(LoginReqDto loginReqDto, String remember, HttpServletResponse response) {
        if (loginReqDto.getUsername() == null || loginReqDto.getUsername().isEmpty()) {
            throw new CustomException("username을 작성해주세요");
        }

        if (loginReqDto.getPassword() == null || loginReqDto.getPassword().isEmpty()) {
            throw new CustomException("password을 작성해주세요");
        }
        User principal = userService.로그인(loginReqDto);

        if (remember == null) {
            remember = "";
        }

        if (remember.equals("on")) {
            Cookie cookie = new Cookie("remember", loginReqDto.getUsername());
            response.addCookie(cookie);
        } else {
            Cookie cookie = new Cookie("remember", "");
            response.addCookie(cookie);
        }
        session.setAttribute("principal", principal);
        return "redirect:/";
    }

    @GetMapping("loginForm")
    public String loginForm(HttpServletRequest request) {

        String username = "";

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("remember")) {
                username = cookie.getValue();
            }
        }
        request.setAttribute("remember", username);
        return "user/loginForm";
    }

    @PostMapping("/join")
    public String join(JoinReqDto joinReqDto) {

        if (joinReqDto.getUsername() == null || joinReqDto.getUsername().isEmpty()) {
            throw new CustomException("username을 작성해주세요");
        }
        User userPS = userRepository.findByUsername(joinReqDto.getUsername());
        if (userPS != null) {
            throw new CustomException("동일한 username 이 존재합니다");
        }

        if (joinReqDto.getPassword() == null || joinReqDto.getPassword().isEmpty()) {
            throw new CustomException("password을 작성해주세요");
        }

        if (joinReqDto.getEmail() == null || joinReqDto.getEmail().isEmpty()) {
            throw new CustomException("email을 작성해주세요");
        }
        userService.회원가입(joinReqDto);
        return "redirect:/loginForm";
    }

    @GetMapping("/user/profileUpdateForm")
    public String profileUpdateForm(Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "redirect:/loginForm";
        }
        User userPS = userRepository.findById(principal.getId());
        model.addAttribute("user", userPS);
        return "user/profileForm";
    }

    @GetMapping("joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

}
