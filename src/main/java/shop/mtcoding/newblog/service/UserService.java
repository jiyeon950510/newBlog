package shop.mtcoding.newblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import shop.mtcoding.newblog.dto.user.UserReq.JoinReqDto;
import shop.mtcoding.newblog.dto.user.UserReq.LoginReqDto;
import shop.mtcoding.newblog.dto.user.UserReq.UpdateReqDto;
import shop.mtcoding.newblog.handler.ex.CustomApiException;
import shop.mtcoding.newblog.handler.ex.CustomException;
import shop.mtcoding.newblog.model.User;
import shop.mtcoding.newblog.model.UserRepository;
import shop.mtcoding.newblog.util.PasswordHash;
import shop.mtcoding.newblog.util.PathUtil;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void 회원정보수정(UpdateReqDto updateReqDto, int principalId) {
        User userPS = userRepository.findById(principalId);
        if (userPS == null) {
            throw new CustomException("회원정보가 존재하지 않습니다.");
        }

        try {
            userRepository.updateById(principalId, updateReqDto.getUsername(), updateReqDto.getPassword(),
                    updateReqDto.getEmail(), userPS.getProfile(), userPS.getCreatedAt());
        } catch (Exception e) {
            throw new CustomException("update 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Transactional
    public void 회원가입(JoinReqDto joinReqDto) {

        User sameUser = userRepository.findByUsername(joinReqDto.getUsername());
        if (sameUser != null) {
            throw new CustomException("동일한 username이 존재합니다");
        }
        if (!joinReqDto.getPassword().equals(joinReqDto.getPasswordCheck())) {
            throw new CustomException("password 가 일치하지 않습니다");
        }
        String password = PasswordHash.getPasswordHash(joinReqDto.getPassword());
        int result = userRepository.insert(joinReqDto.getUsername(), password, joinReqDto.getEmail());
        if (result != 1) {
            throw new CustomException("회원가입실패");
        }
    }

    @Transactional(readOnly = true)
    public User 로그인(LoginReqDto loginReqDto) {
        String password = PasswordHash.getPasswordHash(loginReqDto.getPassword());

        User principal = userRepository.findByUsernameAndPassword(
                loginReqDto.getUsername(), password);
        if (principal == null) {
            throw new CustomException("유저네임 혹은 패스워드가 잘못 입력되었습니다.");
        }
        return principal;
    }

    @Transactional
    public User 프로필사진수정(int principalId, MultipartFile profile) {
        User userPS = userRepository.findById(principalId);
        if (userPS == null) {
            throw new CustomException("회원정보가 존재하지 않습니다.");
        }

        String uuidImageName = PathUtil.writeImageFile(profile);
        userPS.setProfile(uuidImageName);

        try {
            // 2번 저장된 파일의 경로를 DB에 저장
            userRepository.updateById(userPS.getId(), userPS.getUsername(), userPS.getPassword(), userPS.getEmail(),
                    userPS.getProfile(), userPS.getCreatedAt());
        } catch (Exception e) {
            throw new CustomException("사진을 웹서버에 저장하지 못하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR); // 500번 에러
        }

        return userPS;
    }
}
