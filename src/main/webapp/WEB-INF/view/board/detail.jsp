<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../layout/header.jsp" %>

<%-- <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> --%>

<input type="hidden" id="boardId" value="${board.id}" />

            <div class="container my-3">
                <c:if test="${board.userId == principal.id}">
                    <div class="mb-3">
                        <a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
                        <button onclick="deleteById(${board.id})" class="btn btn-danger">삭제</button>
                    </div>
                </c:if>
                    <div class="mb-2">
                        글 번호 : <span id="id"><i>${board.id}</i></span> 작성자 : <span><i>${board.username}</i></span>
                        <c:choose>
                            <c:when test="${love==null}">
                                 <i id="heart" class="fa-regular fa-heart fa-lg" value="${love.id}" onClick="loveUpdate()" style="margin-left: 3%;"></i>
                            </c:when>
                            
                            <c:otherwise>
                                <i id="heart" class="fa-solid fa-heart fa-lg" value="${love.id}" onClick="loveUpdate()" style="margin-left: 3%;"></i>
                            </c:otherwise>
                        </c:choose>
                        
                    </div>

<script>
    function loveUpdate() {
        let boardId = $("#boardId").val();
        let id = $("#heart").attr("value");

        if (id=="") {

            let data = {boardId:boardId}
            
            $.ajax({
                type: "post",
                url: "/love",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            }).done((res) => { // 20X 일때
                $("#heart").attr("value",res.data);
                $("#heart").addClass("fa-solid");
                $("#heart").removeClass("fa-regular");
             
            }).fail((err) => {
                alert(err.responseJSON.msg);
            });

        } 
        else {
                        
           $.ajax({
                type: "delete",
                url: "/love/" + id,
                dataType: "json"
            }).done((res) => { // 20X 일때
                $("#heart").attr("value",res.data);
                $("#heart").removeClass("fa-solid");
                $("#heart").addClass("fa-regular");
            }).fail((err) => { // 40X, 50X 일때
                alert(err.responseJSON.msg);
                });
        }

    }
</script>
                

                    <div>
                        <h3>${board.title}</h3>
                    </div>
                <hr />
                    <div>
                        <div>${board.content}</div>
                    </div>
                    <hr />

                    <div class="card">
                        <form action="/board/${board.id}/reply" method="post">
                            <div class="card-body">
                                <textarea name="comment" id="reply-comment" class="form-control" rows="1"></textarea>
                            </div>
                            <div class="card-footer">
                                <button type="submit" id="btn-reply-save" class="btn btn-primary">등록</button>
                            </div>
                        </form>
                    </div>
                    <br />
                     <div class="card">
                <div class="card-header">댓글 리스트</div>
                    <ul id="reply-box" class="list-group">
                        <c:forEach items="${replyDtoList}" var="reply">
                            <li id="reply-${reply.id}" class="list-group-item d-flex justify-content-between">
                                <div>${reply.comment}</div>
                                <div class="d-flex">
                                    <div class="font-italic">작성자 : ${reply.username} &nbsp;</div>
                                    <c:if test="${principal.id==reply.userId}" >
                                        <button onClick="deleteReply(${reply.id})" class="badge bg-danger">삭제</button>
                                    </c:if>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
               <script>
                        function deleteById(boardId) {
                            $.ajax({
                                type: "delete",
                                url: "/board/" + boardId,
                                dataType: "json"
                            }).done((res) => { // 20X 일때
                                alert(res.msg);
                                location.href = "/";
                            }).fail((err) => { // 40X, 50X 일때
                                alert(err.responseJSON.msg);
                            });
                        }

                        function deleteReply(replyId) {
                            let boardId = $("#boardId").val();
                            $.ajax({
                                type: "delete",
                                url: "/reply/" + replyId,
                                dataType: "json"
                            }).done((res) => { // 20X 일때
                                console.log(boardId);
                                alert(res.msg);
                                location.href = "/board/"+boardId;
                            }).fail((err) => { // 40X, 50X 일때
                                alert(err.responseJSON.msg);
                            });
                        }
                    </script>

        <%@ include file="../layout/footer.jsp" %>