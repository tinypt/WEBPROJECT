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

            function checkpass() {
                if (document.getElementById("p1").value == document.getElementById("p2").value) {
                    document.getElementById("subbtn2").disabled = false;
                } else {
                    document.getElementById("subbtn2").disabled = true;
                }
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
                    <div class="card mt-2" style="width: 61rem; height: 41rem; box-shadow: 0 2px 2px 0 #b6b6b6;">
                        <form action="UpdateAccount" method="post" enctype="multipart/form-data">
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
                                        <p class="pl-4">อัพโหลดรูป: <input type="file" name="file" onchange="Allowbtn()"/></p>
                                    </div>
                                </div>
                                <div class="col-9 mt-2 ml-5 card p-0 " style=" box-shadow: 0 2px 2px 0 #b6b6b6;">
                                    <div class="card-header text-left" style="height: 50px;">
                                        <p>ข้อมูลของคุณ</p>
                                    </div>
                                    <div class="card-body text-left">
                                        <input type="hidden" name="username" value="${acc.username}">
                                        email: <input type="email" class="form-control " name="email" value="${acc.email}" onchange="Allowbtn()" readonly><br>
                                        Name: <input type="text" class="form-control " name="name" value="${acc.name}" onchange="Allowbtn()"><br>
                                        Surname: <input type="text" class="form-control " name="surname" value="${acc.surname}" onchange="Allowbtn()"><br>
                                        Address: <textarea class="form-control" rows="4" cols="30" name="address" onchange="Allowbtn()">${acc.address}</textarea><br>
                                        Telno: <input type="tel" class="form-control " name="telno" value="${acc.telnumber}" pattern="[0-9]{10}" onchange="Allowbtn()"><br>
                                        <div class="row"> 
                                            <div class="col-6">
                                                <a href="#" class="btn btn-warning" data-toggle="modal" data-target="#myModal">
                                                    เปลี่ยนรหัสผ่าน
                                                </a>
                                            </div>
                                            <div class="col-6 text-right">
                                                <input type="submit" class="btn btn-primary" value="UPDATE Account" id="subbtn" disabled="true">

                                            </div></div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="col"></div>
            </div>
        </div>
        <!-- The Modal -->
        <div class="modal" id="myModal">
            <div class="modal-dialog" style="top: 150px;">
                <div class="modal-content">

                    <!-- Modal Header -->
                    <div class="modal-header">
                        <h4 class="modal-title">เปลี่ยนรหัสผ่าน</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>

                    <!-- Modal body -->
                    <form action="ChangePassInacc" method="post">
                        <div class="modal-body">
                            <table>
                                <tr>
                                    <td>Old password: </td>
                                    <td><input type="password" name="oldpass" required></td> 
                                </tr> 
                                <tr>
                                    <td>New password: </td>
                                    <td><input id="p1" type="password" name="newpass" onchange="checkpass()" required></td> 
                                </tr>
                                <tr>
                                    <td>Confirm password: </td>
                                    <td><input id="p2" type="password" name="connewpass" onchange="checkpass()" required></td> 
                                </tr>
                            </table>
                            <br>
                            <input type="hidden" name="username" value="${acc.username}">
                        </div>

                        <!-- Modal footer -->
                        <div class="modal-footer">
                            <input type="submit" id="subbtn2" class="btn btn-primary" disabled>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </body>
</html>
