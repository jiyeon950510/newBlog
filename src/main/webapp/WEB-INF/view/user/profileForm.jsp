<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>

        <style>
            .container {
                display: flex;
                flex-direction: column;
                align-items: center;
            }

            h2 {
                margin-top: 2rem;
            }

            form {
                width: 50%;
                margin-top: 2rem;
                display: flex;
                flex-direction: column;
                align-items: center;
                /* border: 1px solid gray; */
                padding: 1rem;
                border-radius: 10px;
            }

            .form-group {
                margin-bottom: 1rem;
                text-align: center;
            }

            .form-group img {
                width: 290px;
                height: 270px;
                border-radius: 50%;
                margin-bottom: 1rem;
                border: 1px solid gray;
            }

            .btn {
                margin-top: 1rem;
                width: 20%;
            }
        </style>

        <div class="container my-3">
            <h2 class="text-center">프로필 사진 변경 페이지</h2>
            <form action="/user/profileUpdate" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <img src="${principal.profile == null ? '/images/profile.jfif' : principal.profile}" alt="Current Photo"
                        class="img-fluid" id="imagePreview">
                </div>
                <div class="form-group">
                    <input type="file" class="form-control" id="profile" name="profile" onchange="chooseImage(this)">
                </div>
                <button type="submit" class="btn btn-primary">사진변경</button>
            </form>
        </div>

        <script>
            function updateImage() {
            let profileForm = $("#profileForm")[0];
            let formData = new FormData(profileForm);
            console.log("실행");
                $.ajax({
                    type: "put",
                    url: "/user/profileUpdate",
                    data: formData,
                    contentType: false,
                    processData: false,
                    enctype: "multipart/form-data",
                    dataType: "json"
                }).done((res) => {
                    console.log('성공');
                    alert(res.msg);
                    location.href="/"; // 페이지 이동
                }).fail((err) => {
                    console.log('실패');
                    alert(err.responseJSON.msg);
                });
            }

            function chooseImage(obj) {

                let f = obj.files[0];
                console.log(f);

                if (!f.type.match("image.*")) {
                    alert(`이미지를 등록해야 합니다`);
                    return;
                }

                let reader = new FileReader();
                reader.readAsDataURL(f);

                reader.onload = function(e){
                    document.querySelector(`#imagePreview`).setAttribute("src", e.target.result);
                    console.log(e); // context의 정보
                }
            }
        </script>
        <%@ include file="../layout/footer.jsp" %>