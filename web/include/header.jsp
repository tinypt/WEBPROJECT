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
</head>

<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <div class="container">
        <div class="row">
       
    <!-- Brand -->
    <a class="navbar-brand ml-1 col-1" href="montho.jsp">
        <img src="extra-image/logo.png" alt="logo fail" style="width: 30px;">
    </a>
    <!-- Links -->
    <ul class="navbar-nav ml-0 text-center col-10">
        <li class="nav-item col-2">
            <a class="nav-link" href="#">ต้ม</a>
        </li>
        <li class="nav-item col-2">
            <a class="nav-link" href="#">นึ่ง</a>
        </li>
        <li class="nav-item col-2">
            <a class="nav-link" href="#">กวน</a>
        </li>
        <li class="nav-item col-2">
            <a class="nav-link" href="#">เชื่อม</a>
        </li>
        <li class="nav-item col-2">
            <a class="nav-link" href="#">ทอด</a>
        </li>

        <form class="form-inline col-5 ml-4 mr-0 " action="Search" method="post">
            <input class="form-control mr-sm-2 form-control-sm" type="text" placeholder="ค้นหาชื่อขนม" name="name">
                  <button class="btn btn-success btn-sm " type="submit">Search</button>
          
        </form>
        
        <!-- Dropdown -->
        <li class="nav-item dropdown col-3 ml-0">
            <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                <img src="extra-image/cart.png" alt="cart fail" style="width: 30px;" />
                ${acc.name}
            </a>
            <div class="dropdown-menu">
                <a class="dropdown-item" href="#">ตะกร้าสินค้า</a>
                <a class="dropdown-item" href="#">รายการโปรด</a>
                <a class="dropdown-item" href="#">วิดีโอ</a>
                <a class="dropdown-item" href="#">ประวัติการสั่งซื้อ</a>
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
