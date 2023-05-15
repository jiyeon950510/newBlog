package shop.mtcoding.newblog.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import shop.mtcoding.newblog.dto.board.BoardReq.BoardSaveReqDto;
import shop.mtcoding.newblog.dto.board.BoardReq.BoardUpdateReqDto;
import shop.mtcoding.newblog.dto.board.BoardResp.BoardDetailRespDto;
import shop.mtcoding.newblog.dto.board.BoardResp.BoardMainRespDto;

@Mapper
public interface BoardRepository {
        public List<BoardMainRespDto> findAllWithUser();

        public List<Board> findAll();

        public Board findById(int id);

        public int insert(@Param("boardSaveReqDto") BoardSaveReqDto boardSaveReqDto, @Param("userId") int userId,
                        @Param("thumbnail") String thumbnail);

        public int updateById(@Param("id") int id, @Param("boardUpdateReqDto") BoardUpdateReqDto boardUpdateReqDto,
                        @Param("thumbnail") String thumbnail);

        public int deleteById(int id);

        public BoardDetailRespDto findByIdWithUser(int id);
}
