<%-- 
    Document   : Account
    Created on : Nov 1, 2018, 11:03:07 PM
    Author     : GT62VR
--%>

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
    </head>
    <body>
        <jsp:include page="include/header.jsp"/>

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
