<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

<div class="container my-3">
        <div class="container">
            <form action="/user/update" method="post">
                <div class="form-group mb-2">
                    <input type="text" name="username" class="form-control" value="${userPS.username}" id="username">
                </div>

                <div class="form-group mb-2">
                    <input type="password" name="password" class="form-control" value="${userPS.password}" id="password">
                </div>

                <div class="form-group mb-2">
                    <input type="email" name="email" class="form-control" value="${userPS.email}" id="email">
                </div>

                <button type="submit" class="btn btn-primary">회원수정</button>
            </form>

        </div>
    </div>


<%@ include file="../layout/footer.jsp" %>