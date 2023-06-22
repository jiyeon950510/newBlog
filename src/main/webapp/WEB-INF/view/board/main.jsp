<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../layout/header.jsp" %>

    
    <div class="container my-3">
        <div>
            <div class="my-board-box row">
                <c:forEach items="${boardList}" var="boardList">
                    <div class="card col-lg-3 pt-2" >
                        <img class="card-img-top" style="height: 250px;" src="${boardList.thumbnail}" alt="Card image">
                        <div class="card-body">
                            <div>작성자 : ${boardList.username}</div>
                            <h4 class="card-title my-text-ellipsis">${boardList.title}</h4>
                            <a href="/board/${boardList.id}" class="btn btn-primary">상세보기</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div style="width: 100%; display: flex; justify-content: flex-end;">
                <form  id="search" action="/board" method="get" class="d-flex m-2" role="search">
                    <input value="${search}" name="search" class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                    <button class="btn btn-outline-success" type="submit">Search</button>
                   

                </form>
            </div>
        </div>
    </div>
<div>
    <div>
        <div>
            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center">
                
  
                    <c:if test="${paging.startPage != 1 }">
                        <li class="page-item">
                            <a class="page-link" href="/board?nowPage=${paging.startPage - 1 }&cntPerPage=${paging.cntPerPage}">Previous</a>
                        </li>
                    </c:if>

                    
                    <c:forEach begin="${paging.startPage }" end="${paging.endPage }" var="p">
                        <c:choose>
                            <c:when test="${p == paging.nowPage }">
                                <li class="page-item"><b class="page-link">${p }</b></li>
                            </c:when>
                            <c:when test="${p != paging.nowPage }">
                                <li class="page-item"><a class="page-link" href="/board?search=${search}&nowPage=${p }&cntPerPage=${paging.cntPerPage}">${p }</a></li>
                            </c:when>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${paging.endPage != paging.lastPage}">
                        <li class="page-item">
                            <a class="page-link" href="/board?search=${search}&nowPage=${paging.endPage+1 }&cntPerPage=${paging.cntPerPage}">Next</a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>
</div>
        <%@ include file="../layout/footer.jsp" %>