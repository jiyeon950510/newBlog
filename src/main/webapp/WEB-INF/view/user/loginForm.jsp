<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

<div class="container my-3">
        <div class="container">
            <form action="/login" method="post">
                <div class="form-group mb-1">
                    <input type="text" name="username" class="form-control" value="${remember}" placeholder="Enter username" id="username">
                    유저네임을 기억할까요? <input type="checkbox" name="remember" ${remember != "" ? 'checked' : ''}><br />
                </div>

                <div class="form-group mb-2">
                    <input type="password" name="password" class="form-control" placeholder="Enter password"
                        id="password">
                </div>

                <button type="submit" class="btn btn-primary">로그인</button>
            </form>

        </div>
    </div>

<%@ include file="../layout/footer.jsp" %>