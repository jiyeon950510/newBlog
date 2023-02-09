package shop.mtcoding.newblog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.newblog.dto.board.BoardResp.BoardMainRespDto;
import shop.mtcoding.newblog.model.BoardRepository;

@MybatisTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void findAllWithUser_test() throws Exception {
        // given
        ObjectMapper om = new ObjectMapper();

        // when
        List<BoardMainRespDto> BoardMainRespDto = boardRepository.findAllWithUser();
        String responseBody = om.writeValueAsString(BoardMainRespDto);
        System.out.println("테스트 : " + responseBody);

        // then
        assertThat(BoardMainRespDto.get(5).getUsername()).isEqualTo("love");
    }
}
