<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>



 <div class="container my-3">
        <div class="container">
            <form action="/join" method="post" onsubmit="return valid()">
                <div class="form-group mb-2">
                    <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
                </div>

                <div class="form-group mb-2">
                    <input type="password" name="password" class="form-control" placeholder="Enter password"
                        id="password">
                </div>

                <div class="form-group is-invalid">
                    <input type="password" name="passwordCheck" class="form-control" placeholder="Enter passwordCheck" id="passwordCheck" onchange="check()" >
                </div>


                    <div id="check" class="invalid-feedback mb-1 m-1" >
                        PASSWORD 가 동일하지 않습니다
                    </div>


                <div class="form-group mt-2 mb-2">
                    <input type="email" name="email" class="form-control" placeholder="Enter email" id="email">
                </div>

                <button type="submit" class="btn btn-primary">회원가입</button>
            </form>

            <script>
                function check() {
                    let password = $("#password").val();
                    let passwordCheck = $("#passwordCheck").val();
                 
                    if (password!==passwordCheck) {
                        $("#check").show();
                    } else{
                        $("#check").hide();
                    }
                }
                    $(document).ready(function() {
                    $("#check").hide();
                });
            </script>

        </div>
    </div>

<%@ include file="../layout/footer.jsp" %>