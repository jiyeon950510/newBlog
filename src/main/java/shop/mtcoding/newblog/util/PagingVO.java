package shop.mtcoding.newblog.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingVO {
    private int nowPage, startPage, endPage, cntPerPage, total, lastPage, start, end;
    private int cntPage = 5;

    // nowPage(현재페이지), startPage(시작페이지), endPage(끝페이지), cntPerPage(페이지당 글 수),
    // total(글 총 수), lastPage(마지막페이지), start, end => 쿼리에 쓸 변수

    public PagingVO() {
    }

    public PagingVO(int total, int nowPage, int cntPerPage) {
        setNowPage(nowPage);
        setCntPerPage(cntPerPage);
        setTotal(total);
        calcLastPage(getTotal(), getCntPerPage());
        calcStartEndPage(getNowPage(), cntPage);
        calcStartEnd(getNowPage(), getCntPerPage());
    }

    // 제일 마지막 페이지 계산O
    public void calcLastPage(int total, int cntPerPage) {
        setLastPage((int) Math.ceil((double) total / (int) cntPerPage));
        // ceil 소수점 앞으로 올림. (int 캐스팅)
    }

    // 시작, 끝 페이지 계산
    public void calcStartEndPage(int nowPage, int cntPage) {
        setEndPage(((int) Math.ceil((double) nowPage / (double) cntPage)) * cntPage);
        if (getLastPage() < getEndPage()) {
            setEndPage(getLastPage());
            // 마지막 페이지 < 현재 뷰의 끝페이지 =>현재 뷰의 끝 페이지가 마지막 페이지
        }
        setStartPage(getEndPage() - cntPage + 1);
        if (getStartPage() < 1) {
            setStartPage(1);
            // 시작페이지가 1 보다 작으면, 시작페이지는 1 페이지
        }
    }

    // DB 쿼리에서 사용할 start, end값 계산
    public void calcStartEnd(int nowPage, int cntPerPage) {
        setEnd(nowPage * cntPerPage);
        setStart(getEnd() - cntPerPage + 1);
        // 현재페이지 * 게시글 수(8개) = 현재페이지 마지막 게시글 id
    }

    @Override
    public String toString() {
        return "PagingVO [nowPage=" + nowPage + ", startPage=" + startPage + ",endPage=" + endPage + ", total=" + total
                + ", cntPerPage=" + cntPerPage + ", lastPage=" + lastPage + ", start=" +
                start + ", end=" + end
                + ", cntPage=" + cntPage + "]";
    }

}
