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
    </head>
    <body>
    <center>
        <jsp:include page="include/header.jsp"/>
        <h1>Your Detail</h1>
        <table>

            <tr>
                <td>ชื่อลูกค้า</td>
                <td><input type="text" value="${acc.name} ${acc.surname}" name="customername"></td> 
            </tr> 
            <tr>
                <td>ที่อยู่ที่จัดส่ง</td>
                <td><input type="text" value="${acc.address}" name="address"></td>
            </tr>
            <tr>
                <td>โทรศัพท์</td>
                <td><input type="text" value="${acc.telnumber}" name="telno"</td>
            </tr>
        </table>

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
    </center>
    <!--<div>-->
        <a href="Cart.jsp" style="text-align: left; margin-left: 100px;">กลับ</a>
        <!--<a href="Checkout" style="text-align: center;">ยืนยันการสั่งซื้อ</a>-->
        <a href="Checkout" style="text-align: right;">ยืนยันการสั่งซื้อ</a>
    <!--</div>-->
</body>
</html>
