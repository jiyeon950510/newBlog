package shop.mtcoding.newblog.dto.user;

import lombok.Getter;
import lombok.Setter;

public class UserReq {
    @Getter
    @Setter
    public static class JoinReqDto {
        private String username;
        private String password;
        private String email;
    }
}
