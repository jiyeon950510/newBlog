package shop.mtcoding.newblog.dto.reply;

import lombok.Getter;
import lombok.Setter;

public class ReplyResp {

    @Getter
    @Setter
    public static class ReplyDetailRespDto {
        private Integer id;
        private String comment;
        private Integer boardId;
        private Integer userId;
        private String username;
    }
}
