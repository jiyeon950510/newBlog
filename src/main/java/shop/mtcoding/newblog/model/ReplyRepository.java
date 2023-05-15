package shop.mtcoding.newblog.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import shop.mtcoding.newblog.dto.reply.ReplyResp.ReplyDetailRespDto;

@Mapper
public interface ReplyRepository {
    public List<Reply> findAll();

    public Reply findById(int id);

    public int insert(@Param("comment") String comment, @Param("boardId") int boardId, @Param("userId") int userId);

    public int deleteById(int id);

    public int updateById(@Param("id") int id, @Param("comment") String comment, @Param("boardId") int boardId,
            @Param("userId") int userId);

    public List<ReplyDetailRespDto> findByBoardId(Integer boardId);

}