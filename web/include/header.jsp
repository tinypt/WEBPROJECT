<%-- 
    Document   : header1
    Created on : Oct 31, 2018, 11:03:12 PM
    Author     : GT62VR
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<head>
    <!--<title>Test</title>-->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

    <!--<link href="https://fonts.googleapis.com/css?family=Chakra+Petch" rel="stylesheet">-->
    <link href="https://fonts.googleapis.com/css?family=Prompt" rel="stylesheet">
    <style>
        body {
            /*font-family: 'Chakra Petch', sans-serif;*/
            font-family: 'Prompt', sans-serif;
        }

        a:hover {
            color: lightgray;
        }

    </style>
</head>

<nav class="navbar navbar-expand-sm" style=" background-color: #333;">
    <div class="container">
        <div class="row">

            <!-- Brand -->
            <a class="navbar-brand ml-1 col-1" href="montho.jsp">
                <img src="extra-image/logo.png" alt="logo fail" style="width: 30px;">
            </a>
            <!-- Links -->
            <ul class="navbar-nav ml-0 text-center col-10">
                <li class="nav-item col-2">
                    <a class="nav-link text-white" href="Getproduct?type=1">ต้ม</a>
                </li>
                <li class="nav-item col-2">
                    <a class="nav-link text-white" href="Getproduct?type=2">นึ่ง</a>
                </li>
                <li class="nav-item col-2">
                    <a class="nav-link text-white" href="Getproduct?type=3">กวน</a>
                </li>
                <li class="nav-item col-2">
                    <a class="nav-link text-white" href="Getproduct?type=4">เชื่อม</a>
                </li>
                <li class="nav-item col-2">
                    <a class="nav-link text-white" href="Getproduct?type=5">ทอด</a>
                </li>

                <form class="form-inline col-6 ml-4 mr-0" action="Search" method="post">
                    <input class="form-control mr-sm-2 form-control-sm" type="text" placeholder="ค้นหาชื่อขนม" name="name">
                    <button class="btn btn-light btn-sm " type="submit">ค้นหา</button>

                </form>

                <!-- Dropdown -->
                <li class="nav-item dropdown col-3 ml-0">
                    <a class="nav-link dropdown-toggle text-white" href="#" id="navbardrop" data-toggle="dropdown">
                        <img src="extra-image/cart.png" alt="cart fail" style="width: 30px;" />
                        ${acc.username}
                    </a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="Cart.jsp">ตะกร้าสินค้า 
                            <c:choose>
                                <c:when test="${cart.size==null || cart.size==0}">
                                    (0)
                                </c:when>
                                <c:otherwise>
                                    (${cart.size})
                                </c:otherwise>
                            </c:choose></a>
                        <a class="dropdown-item" href="GetFav">รายการโปรด</a>
                        <a class="dropdown-item" href="#">วิดีโอ</a>
                        <a class="dropdown-item" href="GetOrder">ประวัติการสั่งซื้อ</a>
                        <c:if test="${acc!=null}">
                            <a class="dropdown-item" href="Account.jsp">ข้อมูลสมาชิก</a>
                        </c:if>
                        <a class="dropdown-item" href="${acc==null?"Login":"Logout"}">${acc==null?"Log-in":"Log-out"}</a>
                    </div>
                </li>
            </ul>
        </div>  
    </div>

</nav>
