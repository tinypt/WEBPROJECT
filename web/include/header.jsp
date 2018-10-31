<%-- 
    Document   : header1
    Created on : Oct 31, 2018, 11:03:12 PM
    Author     : GT62VR
--%>

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
    <!-- Brand -->
    <a class="navbar-brand" href="montho.jsp">
        <img src="extra-image/logo.png" alt="logo fail" style="width: 40px;">
    </a>
    <!-- Links -->
    <ul class="navbar-nav">
        <li class="nav-item">
            <a class="nav-link" href="#">ประเภทต้ม</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#">ประเภทนึ่ง</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#">ประเภทกวน</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#">ประเภทเชื่อม</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#">ประเภททอด</a>
        </li>

        <form class="form-inline" action="Search" method="post">
            <input class="form-control mr-sm-2" type="text" placeholder="หาตามชื่อขนม" name="name">
            <button class="btn btn-success" type="submit">Search</button>
        </form>
        
        <!-- Dropdown -->
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                <img src="extra-image/cart.png" alt="cart fail" style="width: 40px;" />
                ${acc.name}
            </a>
            <div class="dropdown-menu">
                <a class="dropdown-item" href="#">ตะกร้าสินค้า</a>
                <a class="dropdown-item" href="#">รายการโปรด</a>
                <a class="dropdown-item" href="#">วิดีโอ</a>
                <a class="dropdown-item" href="#">ประวัติการสั่งซื้อ</a>
                <a class="dropdown-item" href="${acc==null?"Login":"Logout"}">${acc==null?"Log-in":"Log-out"}</a>
            </div>
        </li>
    </ul>


</nav>
