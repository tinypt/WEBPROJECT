<%-- 
    Document   : Checkout
    Created on : Nov 12, 2018, 2:06:32 PM
    Author     : Hong
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout Page</title>

        <link href="https://fonts.googleapis.com/css?family=Prompt" rel="stylesheet">
        <style>
            body {
                font-family: 'Prompt', sans-serif;
                font-size: 30px;
            }
            .table {
                font-size: 17px;
            }
            .btn {
                background-color: #86B404;
                color:white;
            }
        </style>
        <script>
            function Allowbtn() {
                document.getElementById("subbtn").disabled = false;
            }
        </script>
    </head>
    <body>
    <center>
        <jsp:include page="include/header.jsp"/>
        <h1>ข้อมูลที่จัดส่ง</h1>
        <form action="UpdateAccount" method="post">
            <table>
                <tr>
                    <td>ชื่อลูกค้า</td>
                    <td><input type="text" value="${acc.name} ${acc.surname}" name="name" onchange="Allowbtn()"></td> 
                </tr> 
                <tr>
                    <td>ที่อยู่ที่จัดส่ง</td>
                    <td><input type="text" value="${acc.address}" name="address" onchange="Allowbtn()"></td>
                </tr>
                <tr>
                    <td>โทรศัพท์</td>
                    <td><input type="text" value="${acc.telnumber}" name="telno" onchange="Allowbtn()"></td>
                </tr>
            </table>
                <br>
                <input type="hidden" name="username" value="${acc.username}">
                <input type="hidden" name="form" value="checkout">
                <input type="submit" id="subbtn" class="btn btn-primary" disabled>
        </form>
        <br>
        <br>
        <table id="example" class="table" style="width: 1000px;">
            <thead>
            <th>รายการ</th>
            <th>ชื่อขนม</th>
            <th>ราคาต่อหน่วย (บาท)</th>
            <th>จำนวน (ชิ้น)</th>
            <th>ราคารวม (บาท)</th>
            </thead>


            <c:forEach items="${cart.lineItems}" var="line" varStatus="vs">
                <tr >
                    <td>${vs.count}</td>
                    <td>${line.prod.productName}</td>
                    <td>${line.prod.productPrice}</td>
                    <td>${line.quantity} </td>
                    <td>${line.totalprice}</td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="2"></td>
                <td></td>
                <td>จำนวนสินค้ารวม ${cart.totalQuantityInCart} ชิ้น</td>
                <td>ราคาสินค้ารวม ${cart.totalPriceInCart} บาท</td>
            </tr>
        </table>
        <a href="Cart.jsp">กลับ</a>
        <a href="Checkout" style="text-align: right;">ยืนยันการสั่งซื้อ</a>
    </center>
    <div>
    </div>
</body>
</html>
