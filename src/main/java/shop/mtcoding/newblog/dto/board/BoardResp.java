package shop.mtcoding.newblog.dto.board;

import lombok.Getter;
import lombok.Setter;

public class BoardResp {
    @Getter
    @Setter
    public static class BoardMainRespDto {
        private int id;
        private String title;
        private String username;
    }
}
