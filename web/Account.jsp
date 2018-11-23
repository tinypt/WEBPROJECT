<%-- 
    Document   : Account
    Created on : Nov 1, 2018, 11:03:07 PM
    Author     : GT62VR
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://fonts.googleapis.com/css?family=Prompt" rel="stylesheet">
        <style>
            body {
                font-family: 'Prompt', sans-serif;
            }
        </style>
        <title>Account Page</title>
        <script>
            function Allowbtn() {
                document.getElementById("subbtn").disabled = false;
            }
        </script>
    </head>
    <body>
        <jsp:include page="include/header.jsp"/>
        <c:if test="${wrongtype!=null}">
            <div class="alert alert-danger" role="alert">
                ${wrongtype}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
         <c:if test="${update!=null}">
            <div class="alert alert-success" role="alert" style="margin-bottom: 0px;">
                ${update}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <div class="container">
            <div class="row text-center">
                <div class="col"></div>
                <div class="col">
                    <div class="card p-5" style="width: 61rem; height: 10rem; box-shadow: 0 2px 2px 0 #b6b6b6;">

                        <h2>Your Account</h2>
                        <p class="text-secondary">อัพเดทข้อมูลบัญชีง่ายๆ ในการซื้อสินค้าที่ มณโฑขนมหวาน</p>

                    </div>
                </div>
                <div class="col"></div>
            </div>
            <div class="row text-center">
                <div class="col"></div>
                <div class="col">
                    <div class="card mt-2" style="width: 61rem; height: 35.5rem; box-shadow: 0 2px 2px 0 #b6b6b6;">

                        <div class="row">
                            <div class="col-2 pt-4 pl-4">
                                <p class="text-left">ข้อมูลสมาชิก</p>
                                <div style="text-align: center;" class="pl-3">
                                    <c:choose>
                                        <c:when test="${acc.picturename!=null}">
                                            <img src="image-account/${acc.picturename}" alt="user" width="150px">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="image-account/user.png" alt="user" width="150px">
                                        </c:otherwise>
                                    </c:choose>
                                    <form action="FileUpload" method="post" enctype="multipart/form-data">
                                        <p class="pl-4">อัพโหลดรูป: <input type="file" name="file" onchange="Allowbtn()"/></p>
                                        <p class="pl-0 ml-1">
                                            <input id="subbtn" disabled="true" type="submit" class="btn btn-primary" value="UPDATE Picture">
                                        </p>
                                    </form>
                                </div>
                            </div>
                            <div class="col-9 mt-2 ml-5 card p-0 " style=" box-shadow: 0 2px 2px 0 #b6b6b6;">
                                <div class="card-header text-left" style="height: 50px;">
                                    <p>ข้อมูลของคุณ</p>
                                </div>
                                <div class="card-body text-left">
                                    <form action="UpdateAccount" method="post">
                                        <input type="hidden" name="username" value="${acc.username}">
                                        Name: <input type="text" class="form-control " name="name" value="${acc.name}"><br>
                                        Surname: <input type="text" class="form-control " name="surname" value="${acc.surname}"><br>
                                        Address: <textarea class="form-control" rows="4" cols="30" name="address">${acc.address}</textarea><br>
                                        Telno: <input type="tel" class="form-control " name="telno" value="${acc.telnumber}" pattern="[0-9]{10}"><br>
                                        <input type="submit" class="btn btn-primary" value="UPDATE Account">
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col"></div>
            </div>
        </div>

    </body>
</html>
